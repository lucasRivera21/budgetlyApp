package com.lucasdev.budgetlyapp.features.home.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lucasdev.budgetlyapp.ExpenseCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.domain.models.TagModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "HomeRepository"

interface HomeTask {
    suspend fun getHomeData(): Flow<List<ExpenseEntity>>
    suspend fun fetchNextExpensesUseCase(): Flow<List<NextExpenseDTO>>
    suspend fun fetchRequestCode(taskId: String): Int?
    suspend fun updateIsCompleteTask(taskId: String)
    suspend fun deleteExpenses()
    suspend fun insertExpenses(expenses: List<ExpenseEntity>)
    suspend fun countExpenses(): Int
    suspend fun fetchExpenses(): List<ExpenseApiDTO>
}

class HomeRepository @Inject constructor(
    private val room: AppDatabase,
    private val api: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HomeTask {
    override suspend fun getHomeData() = room.expenseDao().getAllExpenses()

    override suspend fun fetchNextExpensesUseCase() = room.taskDao().getNextTask()

    override suspend fun fetchRequestCode(taskId: String): Int? {
        return try {
            room.taskDao().getRequestCode(taskId.toInt())
        } catch (e: Exception) {
            Log.e(TAG, "fetchRequestCode: ${e.message}")
            null
        }
    }

    override suspend fun updateIsCompleteTask(taskId: String) {
        try {
            room.taskDao().updateTaskCompletion(taskId.toInt(), true)
        } catch (e: Exception) {
            Log.e(TAG, "updateIsCompleteTask: ${e.message}")
        }
    }

    override suspend fun deleteExpenses() = room.expenseDao().deleteAllExpenses()

    override suspend fun insertExpenses(expenses: List<ExpenseEntity>) {
        try {
            room.expenseDao().insertExpenses(expenses)
        } catch (e: Exception) {
            Log.e(TAG, "insertExpenses: ${e.message}")
        }
    }

    override suspend fun countExpenses() = room.expenseDao().countExpenses()

    override suspend fun fetchExpenses(): List<ExpenseApiDTO> {
        try {
            val user = auth.currentUser

            Log.d(TAG, "fetchExpenses: ${user?.uid}")
            user?.let {
                val colRef = api.collection(UsersCollection.collectionName).document(user.uid)
                    .collection(ExpenseCollection.collectionName)

                val snapshot = colRef.get().await()

                val expenseApiDTO = snapshot.documents.map { document ->
                    val mapData = document.data as Map<String, Any>
                    Log.d(TAG, "fetchExpenses: ${document.id} => ${document.data}")

                    val tagMap = mapData["tag"] as Map<*, *>
                    val color = tagMap["color"].toString()
                    val iconId = tagMap["iconId"].toString()
                    val tagId = tagMap["tagId"].toString().toInt()
                    val tagNameId = tagMap["tagNameId"].toString()

                    val tagModel = TagModel(
                        color = color,
                        iconId = iconId,
                        tagId = tagId,
                        tagNameId = tagNameId
                    )

                    ExpenseApiDTO(
                        amount = mapData["amount"].toString().toDouble(),
                        createdAt = mapData["createdAt"].toString(),
                        day = mapData["day"]?.toString()?.toInt(),
                        expenseGroupId = mapData["expenseGroupId"].toString(),
                        expenseName = mapData["expenseName"].toString(),
                        hasNotification = mapData["hasNotification"].toString().toBoolean(),
                        tag = tagModel
                    )
                }

                Log.d(TAG, "fetchExpenses: ${expenseApiDTO.size}")

                return expenseApiDTO
            }

            return emptyList()

        } catch (e: Exception) {
            Log.e(TAG, "fetchExpenses: ${e.message}")
            return emptyList()
        }
    }
}

