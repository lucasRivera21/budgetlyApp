package com.lucasdev.budgetlyapp.common.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lucasdev.budgetlyapp.ExpenseCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseToUpload
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.features.register.presentation.TAG
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

interface ExpenseRepository {
    suspend fun getExpensesToUpload(upLoadStateCode: Int = UploadState.UPLOADED.code): List<ExpenseEntity>

    suspend fun updateExpenses(expenseList: List<ExpenseEntity>): List<Int>

    suspend fun updateIsUploaded(expenseIdList: List<Int>, isUploadStateCode: Int)
}

class ExpenseRepositoryImpl @Inject constructor(
    private val room: AppDatabase,
    private val auth: FirebaseAuth,
    private val api: FirebaseFirestore
) : ExpenseRepository {
    override suspend fun getExpensesToUpload(upLoadStateCode: Int) =
        room.expenseDao().getExpensesToUpload(upLoadStateCode)

    override suspend fun updateExpenses(expenseList: List<ExpenseEntity>): List<Int> {
        val expenseIdUploadedList = mutableListOf<Int>()
        try {
            val user = auth.currentUser
            user?.let {
                expenseList.forEach { expenseEntity ->
                    val expenseToUpload = ExpenseToUpload(
                        expenseId = expenseEntity.expenseId,
                        expenseName = expenseEntity.expenseName,
                        expenseAmount = expenseEntity.expenseAmount,
                        createdAt = expenseEntity.createdAt,
                        day = expenseEntity.day,
                        expenseGroupId = expenseEntity.expenseGroupId,
                        hasNotification = expenseEntity.hasNotification,
                        tagId = expenseEntity.tagId
                    )

                    api.collection(UsersCollection.collectionName).document(it.uid).collection(
                        ExpenseCollection.collectionName
                    ).document().set(expenseToUpload).await()

                    expenseIdUploadedList.add(expenseEntity.expenseId)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "updateExpenses: ${e.message}")
        }

        return expenseIdUploadedList
    }

    override suspend fun updateIsUploaded(
        expenseIdList: List<Int>,
        isUploadStateCode: Int
    ) {
        try {
            room.expenseDao().updateExpensesIsUpload(expenseIdList, isUploadStateCode)
        } catch (e: Exception) {
            Log.e(TAG, "updateIsUploaded: ${e.message}")
        }
    }
}