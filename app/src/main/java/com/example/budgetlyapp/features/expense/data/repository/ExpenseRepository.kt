package com.example.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.example.budgetlyapp.ExpenseCollection
import com.example.budgetlyapp.TaskCollection
import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.features.expense.domain.models.TaskToUploadNotificationResponse
import com.example.budgetlyapp.features.home.domain.models.TaskResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "ExpenseRepository"

interface ExpenseTask {
    suspend fun getExpenseGroupList(): Flow<List<ExpenseModelResponse>>

    suspend fun getTaskList(expenseId: String): List<TaskToUploadNotificationResponse>

    suspend fun getTaskWithMostCurrentDate(): List<TaskResponse>

    suspend fun updateExpenseNotification(
        expenseId: String,
        hasNotification: Boolean
    )

    suspend fun updateRequestCode(expenseId: String, requestCode: Int?, dateDue: String)

    suspend fun deleteExpense(expenseId: String)
}

class ExpenseRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExpenseTask {
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
                trySend(expenseModelResponseList)
            }

            awaitClose { expenseListener.remove() }
        } catch (e: Exception) {
            Log.e(TAG, "getExpenseGroupList: ${e.message}", e)
            close(e)
        }
    }

    override suspend fun getTaskList(expenseId: String): List<TaskToUploadNotificationResponse> {
        val userId = auth.currentUser?.uid
        val taskResponseList = mutableListOf<TaskToUploadNotificationResponse>()
        if (userId != null) {
            try {
                val userRef = db.collection(UsersCollection.collectionName).document(userId)
                val taskRef = userRef.collection(TaskCollection.collectionName)
                val query =
                    taskRef.whereEqualTo("expenseId", expenseId).whereEqualTo("completed", false)
                val taskSnapshot = query.get().await()
                for (taskDocument in taskSnapshot.documents) {
                    val taskData = taskDocument.data as Map<String, Any>

                    val amount = taskData["amount"] as Double
                    val dateDue = taskData["dateDue"] as String
                    val requestCode = taskData["requestCode"] as Long?
                    val taskName = taskData["taskName"] as String

                    val tagMap = taskData["tag"] as? Map<*, *> ?: emptyMap<String, Any>()
                    val iconId = tagMap["iconId"].toString()

                    taskResponseList.add(
                        TaskToUploadNotificationResponse(
                            requestCode = requestCode?.toInt(),
                            iconId = iconId,
                            taskName = taskName,
                            amount = amount,
                            dateDue = dateDue
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, "getTaskList: ${e.message}", e)
            }
        }

        return taskResponseList
    }

    override suspend fun getTaskWithMostCurrentDate(): List<TaskResponse> {
        val userId = auth.currentUser?.uid
        val taskResponseList = mutableListOf<TaskResponse>()

        try {
            val userRef = db.collection(UsersCollection.collectionName).document(userId!!)
            val expenseRef = userRef.collection(ExpenseCollection.collectionName)
            val taskRef = userRef.collection(TaskCollection.collectionName)
            val expenseSnapshot = expenseRef.get().await()

            for (expenseDocument in expenseSnapshot.documents) {
                val expenseId = expenseDocument.id
                val taskSnapshot = taskRef.whereEqualTo("expenseId", expenseId)
                    .orderBy("dateDue", Query.Direction.DESCENDING).limit(1).get().await()

                for (taskDocument in taskSnapshot.documents) {
                    val taskData = taskDocument.data as Map<String, Any>

                    val amount = taskData["amount"].toString().toDouble()
                    val completed = taskData["completed"].toString().toBoolean()
                    val createdAt = taskData["createdAt"].toString()
                    val dateDue = taskData["dateDue"].toString()
                    val expenseGroupId = taskData["expenseGroupId"].toString()
                    val hasDayDue = taskData["hasDayDue"].toString().toBoolean()
                    val hasNotification = taskData["hasNotification"].toString().toBoolean()
                    val taskName = taskData["taskName"].toString()

                    val tagMap = taskData["tag"] as? Map<*, *> ?: emptyMap<String, Any>()
                    val tag = TagModel(
                        tagId = tagMap["tagId"].toString().toInt(),
                        tagNameId = tagMap["tagNameId"].toString(),
                        color = tagMap["color"].toString(),
                        iconId = tagMap["iconId"].toString()
                    )

                    taskResponseList.add(
                        TaskResponse(
                            taskId = taskDocument.id,
                            amount = amount,
                            completed = completed,
                            createdAt = createdAt,
                            dateDue = dateDue,
                            expenseGroupId = expenseGroupId,
                            expenseId = expenseId,
                            hasDayDue = hasDayDue,
                            hasNotification = hasNotification,
                            taskName = taskName,
                            tag = tag
                        )
                    )
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "getExpenseList: ${e.message}", e)
        }

        return taskResponseList
    }

    override suspend fun updateExpenseNotification(
        expenseId: String,
        hasNotification: Boolean
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            try {
                val expenseRef =
                    db.collection(UsersCollection.collectionName).document(userId)
                        .collection(ExpenseCollection.collectionName)
                        .document(expenseId)

                expenseRef.update("hasNotification", hasNotification)
            } catch (e: Exception) {
                Log.e(TAG, "updateExpenseNotification: ${e.message}", e)
            }
        }
    }

    override suspend fun updateRequestCode(expenseId: String, requestCode: Int?, dateDue: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            try {
                val userRef = db.collection(UsersCollection.collectionName).document(userId)
                val taskCollectRef = userRef.collection(TaskCollection.collectionName)
                val query = taskCollectRef.whereEqualTo("expenseId", expenseId)
                    .whereEqualTo("dateDue", dateDue)
                val taskSnapshot = query.get().await()
                for (taskDocument in taskSnapshot.documents) {
                    taskDocument.reference.update("requestCode", requestCode)
                }
            } catch (e: Exception) {
                Log.e(TAG, "updateRequestCode: ${e.message}", e)
            }
        }
    }

    override suspend fun deleteExpense(expenseId: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            try {
                val userRef = db.collection(UsersCollection.collectionName).document(userId)
                val expenseRef = userRef.collection(ExpenseCollection.collectionName)
                    .document(expenseId)

                expenseRef.delete()

                val taskCollectRef = userRef.collection(TaskCollection.collectionName)
                val query = taskCollectRef.whereEqualTo("expenseId", expenseId)
                val taskSnapshot = query.get().await()
                for (taskDocument in taskSnapshot.documents) {
                    taskDocument.reference.delete()
                }

            } catch (e: Exception) {
                Log.e(TAG, "deleteExpense: ${e.message}", e)
            }
        }
    }
}