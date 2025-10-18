package com.lucasdev.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.lucasdev.budgetlyapp.ExpenseCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.lucasdev.budgetlyapp.common.domain.models.TagModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.common.utils.UploadState.Companion.codeToUploadState
import com.lucasdev.budgetlyapp.features.expense.data.dto.TaskToUploadNotificationDTO
import com.lucasdev.budgetlyapp.features.home.data.NextExpenseDTO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "ExpenseRepository"

interface ExpenseTask {
    suspend fun getExpenseList(): Flow<List<ExpenseEntity>>
    suspend fun getExpenseGroupList(): Flow<List<ExpenseModelResponse>>

    suspend fun getTaskList(expenseId: Int): List<TaskToUploadNotificationDTO>

    suspend fun getTaskWithMostCurrentDate(): List<NextExpenseDTO>

    suspend fun updateExpenseNotification(
        expenseId: Int,
        hasNotification: Boolean
    )

    suspend fun updateRequestCode(expenseId: Int, requestCode: Int?, dateDue: String)

    suspend fun deleteExpense(expenseId: Int)
}

class ExpenseRepository @Inject constructor(
    private val room: AppDatabase,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : ExpenseTask {
    override suspend fun getExpenseList(): Flow<List<ExpenseEntity>> =
        room.expenseDao().getAllExpenses()

    override suspend fun getExpenseGroupList(): Flow<List<ExpenseModelResponse>> = callbackFlow {
        try {
            val userId = auth.currentUser!!.uid

            val expenseRef = db.collection(UsersCollection.collectionName)
                .document(userId)
                .collection(ExpenseCollection.collectionName)

            val expenseListener = expenseRef.addSnapshotListener { snapshot, _ ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                val expenseModelResponseList = mutableListOf<ExpenseModelResponse>()

                val documentSnapshot = snapshot.documents
                for (document in documentSnapshot) {
                    val expenseId = document.id
                    val expense = document.data as Map<String, Any>
                    val expenseGroupId = expense["expenseGroupId"] as String
                    val amount = expense["amount"] as Double
                    val dayPay = expense["day"] as Long?
                    val expenseName = expense["expenseName"] as String
                    val hasNotification = expense["hasNotification"] as Boolean
                    val createdAt = expense["createdAt"] as String

                    val tag = expense["tag"] as Map<*, *>
                    val tagIconId = tag["iconId"] as String
                    val tagId = tag["tagId"] as Long
                    val tagName = tag["tagNameId"] as String
                    val tagColor = tag["color"] as String

                    expenseModelResponseList.add(
                        ExpenseModelResponse(
                            expenseId = expenseId,
                            expenseGroupId = expenseGroupId,
                            amount = amount,
                            dayPay = dayPay?.toInt(),
                            expenseName = expenseName,
                            hasNotification = hasNotification,
                            createdAt = createdAt,
                            tag = TagModel(
                                tagId = tagId.toInt(),
                                tagNameId = tagName,
                                color = tagColor,
                                iconId = tagIconId
                            )
                        )
                    )
                }
                trySend(expenseModelResponseList.sortedBy { it.createdAt })
            }

            awaitClose { expenseListener.remove() }
        } catch (e: Exception) {
            Log.e(TAG, "getExpenseGroupList: ${e.message}", e)
            close(e)
        }
    }

    override suspend fun getTaskList(expenseId: Int): List<TaskToUploadNotificationDTO> {
        return try {
            room.taskDao().getTasks(expenseId)
        } catch (e: Exception) {
            Log.e(TAG, "getTaskList: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getTaskWithMostCurrentDate(): List<NextExpenseDTO> {
        return try {
            room.taskDao().getLatestTasks()
        } catch (e: Exception) {
            Log.e(TAG, "getTaskWithMostCurrentDate: ${e.message}")
            emptyList()
        }
    }

    override suspend fun updateExpenseNotification(
        expenseId: Int,
        hasNotification: Boolean
    ) {
        try {
            val isUploadInt = room.expenseDao().getIsUpload(expenseId)
            val uploadState = codeToUploadState(isUploadInt)
            val newUploadState = when (uploadState) {
                UploadState.UPLOADED -> UploadState.EDITED
                else -> uploadState
            }
            room.expenseDao()
                .updateExpenseNotification(expenseId, hasNotification, newUploadState.code)
        } catch (e: Exception) {
            Log.e(TAG, "updateExpenseNotification: ${e.message}")
        }
    }

    override suspend fun updateRequestCode(expenseId: Int, requestCode: Int?, dateDue: String) {
        try {
            room.taskDao().updateTaskRequestCode(expenseId, requestCode, dateDue)
        } catch (e: Exception) {
            Log.e(TAG, "updateRequestCode: ${e.message}")
        }
    }

    override suspend fun deleteExpense(expenseId: Int) {
        try {
            room.expenseDao().updateExpenseIsUpload(expenseId, UploadState.DELETED.code)
            room.taskDao().updateTaskUploadState(expenseId, UploadState.DELETED.code)
        } catch (e: Exception) {
            Log.e(TAG, "deleteExpense: ${e.message}")
        }
    }
}