package com.example.budgetlyapp.features.home.data

import com.example.budgetlyapp.ExpenseCollection
import com.example.budgetlyapp.TaskCollection
import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.features.home.domain.models.TaskResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface HomeTask {
    suspend fun fetchHomeData(): Flow<List<ExpenseModelResponse>>
    suspend fun fetchNextExpensesUseCase(): Flow<List<TaskResponse>>
}

class HomeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HomeTask {
    override suspend fun fetchHomeData(): Flow<List<ExpenseModelResponse>> = callbackFlow {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            close()
            return@callbackFlow
        }

        try {
            val userRef = db.collection(UsersCollection.collectionName).document(userId)
            val expenseRef = userRef.collection(ExpenseCollection.collectionName)

            val expenseListener = expenseRef.addSnapshotListener { snapshot, _ ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }

                val expenseModelResponseList = mutableListOf<ExpenseModelResponse>()
                val expenseDocuments = snapshot.documents

                for (document in expenseDocuments) {
                    val expenseId = document.id
                    val expenseData = document.data as Map<String, Any>

                    val tagMap = expenseData["tag"] as? Map<*, *> ?: emptyMap<String, Any>()
                    val expenseModel = ExpenseModelResponse(
                        expenseId = expenseId,
                        expenseGroupId = expenseData["expenseGroupId"].toString(),
                        amount = expenseData["amount"].toString().toDouble(),
                        dayPay = expenseData["day"].toString().toIntOrNull(),
                        expenseName = expenseData["expenseName"].toString(),
                        hasNotification = expenseData["hasNotification"].toString().toBoolean(),
                        createdAt = expenseData["createdAt"].toString(),
                        tag = TagModel(
                            tagId = tagMap["tagId"].toString().toInt(),
                            tagNameId = tagMap["tagNameId"].toString(),
                            color = tagMap["color"].toString(),
                            iconId = tagMap["iconId"].toString()
                        )
                    )

                    expenseModelResponseList.add(expenseModel)
                }

                trySend(expenseModelResponseList)
            }

            awaitClose {
                expenseListener.remove()
            }
        } catch (e: Exception) {
            close(e)
        }
    }

    override suspend fun fetchNextExpensesUseCase(): Flow<List<TaskResponse>> = callbackFlow {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            close()
            return@callbackFlow
        }

        try {
            val userRef = db.collection(UsersCollection.collectionName).document(userId)
            val taskRef = userRef.collection(TaskCollection.collectionName)

            val query = taskRef.whereEqualTo("completed", false)
            val taskListener = query.addSnapshotListener { snapshot, _ ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }

                val taskResponseList = mutableListOf<TaskResponse>()
                val taskDocuments = snapshot.documents

                for (document in taskDocuments) {
                    val taskId = document.id
                    val taskData = document.data as Map<String, Any>

                    val tagMap = taskData["tag"] as? Map<*, *> ?: emptyMap<String, Any>()
                    val taskResponseModel = TaskResponse(
                        taskId = taskId,
                        amount = taskData["amount"].toString().toDouble(),
                        completed = taskData["completed"].toString().toBoolean(),
                        createdAt = taskData["createdAt"].toString(),
                        dateDue = taskData["dateDue"].toString(),
                        expenseGroupId = taskData["expenseGroupId"].toString(),
                        expenseId = taskData["expenseId"].toString(),
                        hasDayDue = taskData["hasDayDue"].toString().toBoolean(),
                        hasNotification = taskData["hasNotification"].toString().toBoolean(),
                        taskName = taskData["taskName"].toString(),
                        tag = TagModel(
                            tagId = tagMap["tagId"].toString().toInt(),
                            tagNameId = tagMap["tagNameId"].toString(),
                            color = tagMap["color"].toString(),
                            iconId = tagMap["iconId"].toString()
                        )
                    )

                    taskResponseList.add(taskResponseModel)
                }

                trySend(taskResponseList)
            }

            awaitClose {
                taskListener.remove()
            }
        } catch (e: Exception) {
            close(e)
        }
    }
}

