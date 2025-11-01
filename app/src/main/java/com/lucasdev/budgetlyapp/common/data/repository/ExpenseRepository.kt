package com.lucasdev.budgetlyapp.common.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.lucasdev.budgetlyapp.ExpenseCollection
import com.lucasdev.budgetlyapp.TaskCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseResponseDTO
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseToUpload
import com.lucasdev.budgetlyapp.common.domain.models.TaskResponseDTO
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.features.register.presentation.TAG
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

interface ExpenseRepository {
    suspend fun getExpensesToUpload(upLoadStateCode: Int = UploadState.UPLOADED.code): List<ExpenseEntity>

    suspend fun updateExpenses(expense: ExpenseEntity): String?

    suspend fun updateIsUploaded(expenseId: Int, expenseIdRemote: String, isUploadStateCode: Int)

    suspend fun downloadExpenses(): List<ExpenseResponseDTO>

    suspend fun insertExpense(expense: ExpenseEntity)

    suspend fun getExpenseIdLocalByExpenseIdRemote(expenseIdRemote: String): Int

    suspend fun insertTask(taskEntityList: List<TaskEntity>)

    suspend fun deleteExpenseFromApi(expenseIdRemote: String?, taskIdRemoteList: List<String>)

    suspend fun deleteExpenseFromDb(expenseIdLocal: Int)
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

    override suspend fun downloadExpenses(): List<ExpenseResponseDTO> {
        val expenseResponseList = mutableListOf<ExpenseResponseDTO>()
        try {
            val user = auth.currentUser
            if (user != null) {
                val docRef = api.collection(UsersCollection.collectionName).document(user.uid)
                val docSnapshot = docRef.collection(ExpenseCollection.collectionName).get().await()

                for (document in docSnapshot) {
                    val expenseResponseDTO = document.toObject<ExpenseResponseDTO>()

                    val expenseIdRemote = document.id
                    val docTaskSnapshot = docRef.collection(TaskCollection.collectionName)
                        .whereEqualTo("expenseId", expenseIdRemote).get().await()

                    val taskList = mutableListOf<TaskResponseDTO>()
                    for (docTask in docTaskSnapshot) {
                        val taskIdRemote = docTask.id
                        val taskResponseDTO = docTask.toObject<TaskResponseDTO>()
                        val taskResponse = taskResponseDTO.copy(taskIdRemote = taskIdRemote)
                        taskList.add(taskResponse)
                    }

                    val expenseResponse =
                        expenseResponseDTO.copy(expenseIdRemote = document.id, taskList = taskList)
                    expenseResponseList.add(expenseResponse)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "downloadExpenses: ${e.message}")
        }

        return expenseResponseList
    }

    override suspend fun insertExpense(expense: ExpenseEntity) =
        room.expenseDao().insertExpense(expense)

    override suspend fun getExpenseIdLocalByExpenseIdRemote(expenseIdRemote: String) =
        room.taskDao().getExpenseIdLocal(expenseIdRemote)

    override suspend fun insertTask(taskEntityList: List<TaskEntity>) =
        room.taskDao().insertTasks(taskEntityList)

    override suspend fun deleteExpenseFromApi(
        expenseIdRemote: String?,
        taskIdRemoteList: List<String>
    ) {
        try {
            val user = auth.currentUser
            user?.let {
                expenseIdRemote?.let {
                    val userRef = api.collection(UsersCollection.collectionName).document(user.uid)

                    userRef.collection(ExpenseCollection.collectionName).document(expenseIdRemote)
                        .delete().await()

                    taskIdRemoteList.forEach { taskIdRemote ->
                        userRef.collection(TaskCollection.collectionName).document(taskIdRemote)
                            .delete().await()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "deleteExpense", e)
        }
    }

    override suspend fun deleteExpenseFromDb(expenseIdLocal: Int) =
        room.expenseDao().deleteExpenseByExpenseIdLocal(expenseIdLocal)
}