package com.lucasdev.budgetlyapp.features.home.data

import android.util.Log
import com.lucasdev.budgetlyapp.TaskCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "HomeRepository"

interface HomeTask {
    suspend fun getHomeData(): Flow<List<ExpenseEntity>>
    suspend fun fetchNextExpensesUseCase(): Flow<List<NextExpenseDTO>>
    suspend fun fetchRequestCode(taskId: String): Int?
    suspend fun updateIsCompleteTask(taskId: String)
}

class HomeRepository @Inject constructor(
    private val room: AppDatabase,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HomeTask {
    override suspend fun getHomeData() = room.expenseDao().getAllExpenses()

    override suspend fun fetchNextExpensesUseCase() = room.taskDao().getNextTask()

    override suspend fun fetchRequestCode(taskId: String): Int? {
        val userId = auth.currentUser?.uid ?: return null

        try {
            val userRef = db.collection(UsersCollection.collectionName).document(userId)
            val taskRef = userRef.collection(TaskCollection.collectionName).document(taskId)
            val documentSnapshot = taskRef.get().await()
            val taskData = documentSnapshot.data ?: return null
            val requestCode = taskData["requestCode"] as Long?
            return requestCode?.toInt()
        } catch (e: Exception) {
            Log.e(TAG, "fetchRequestCode: ${e.message}", e)
            return null
        }
    }

    override suspend fun updateIsCompleteTask(taskId: String) {
        val userId = auth.currentUser?.uid ?: return

        try {
            val userRef = db.collection(UsersCollection.collectionName).document(userId)

            val taskRef = userRef.collection(TaskCollection.collectionName).document(taskId)
            taskRef.update("completed", true)
        } catch (e: Exception) {
            Log.e(TAG, "updateIsCompleteTask: ${e.message}", e)
        }
    }
}

