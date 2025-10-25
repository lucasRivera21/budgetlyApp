package com.lucasdev.budgetlyapp.common.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lucasdev.budgetlyapp.ExpenseCollection
import com.lucasdev.budgetlyapp.TaskCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseToUpload
import com.lucasdev.budgetlyapp.common.domain.models.TaskToUpload
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.features.register.presentation.TAG
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

interface ExpenseRepository {
    suspend fun getExpensesToUpload(upLoadStateCode: Int = UploadState.UPLOADED.code): List<ExpenseEntity>

    suspend fun updateExpenses(expense: ExpenseEntity): String?

    suspend fun updateIsUploaded(expenseId: Int, expenseIdRemote: String, isUploadStateCode: Int)

    suspend fun updateTask(task: TaskToUpload): String?
}

class ExpenseRepositoryImpl @Inject constructor(
    private val room: AppDatabase,
    private val auth: FirebaseAuth,
    private val api: FirebaseFirestore
) : ExpenseRepository {
    override suspend fun getExpensesToUpload(upLoadStateCode: Int) =
        room.expenseDao().getExpensesToUpload(upLoadStateCode)

    override suspend fun updateExpenses(expense: ExpenseEntity): String? {
        return try {
            val user = auth.currentUser
            user?.let {
                val expenseToUpload = ExpenseToUpload(
                    expenseId = expense.expenseId,
                    expenseName = expense.expenseName,
                    expenseAmount = expense.expenseAmount,
                    createdAt = expense.createdAt,
                    day = expense.day,
                    expenseGroupId = expense.expenseGroupId,
                    hasNotification = expense.hasNotification,
                    tagId = expense.tagId
                )

                val docRef =
                    api.collection(UsersCollection.collectionName).document(it.uid).collection(
                        ExpenseCollection.collectionName
                    ).add(expenseToUpload).await()

                docRef.id
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateExpenses: ${e.message}")
            null
        }
    }

    override suspend fun updateIsUploaded(
        expenseId: Int,
        expenseIdRemote: String,
        isUploadStateCode: Int
    ) {
        try {
            room.expenseDao().updateExpensesIsUpload(
                expenseId = expenseId,
                expenseIdRemote = expenseIdRemote,
                isUpload = isUploadStateCode
            )
        } catch (e: Exception) {
            Log.e(TAG, "updateIsUploaded: ${e.message}")
        }
    }

    override suspend fun updateTask(task: TaskToUpload): String? {
        return try {
            val user = auth.currentUser
            if (user != null) {
                val docRef =
                    api.collection(UsersCollection.collectionName).document(user.uid).collection(
                        TaskCollection.collectionName
                    ).add(task).await()

                docRef.id
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateTask: ${e.message}")
            null
        }
    }
}