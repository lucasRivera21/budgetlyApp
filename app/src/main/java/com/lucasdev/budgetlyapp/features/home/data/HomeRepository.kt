package com.lucasdev.budgetlyapp.features.home.data

import android.util.Log
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "HomeRepository"

interface HomeTask {
    suspend fun getHomeData(): Flow<List<ExpenseEntity>>
    suspend fun fetchNextExpensesUseCase(): Flow<List<NextExpenseDTO>>
    suspend fun fetchRequestCode(taskId: String): Int?
    suspend fun updateIsCompleteTask(taskId: String)
}

class HomeRepository @Inject constructor(
    private val room: AppDatabase
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
}

