package com.lucasdev.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseModel
import com.lucasdev.budgetlyapp.features.expense.domain.models.TaskUpload
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.domain.models.toEntity
import com.lucasdev.budgetlyapp.features.expense.domain.models.toEntity
import javax.inject.Inject

private const val TAG = "CreateExpenseRepository"

interface CreateExpenseTask {
    suspend fun saveExpense(expenseModel: ExpenseModel): Int?
    suspend fun saveTask(taskList: List<TaskUpload>)
}

class CreateExpenseRepository @Inject constructor(
    private val room: AppDatabase
) :
    CreateExpenseTask {
    override suspend fun saveExpense(expenseModel: ExpenseModel): Int? {
        try {
            room.expenseDao().insertExpense(expenseModel.toEntity())
            val lastExpenseId = room.expenseDao().getLastExpenseId()

            return lastExpenseId
        } catch (e: Exception) {
            Log.d(TAG, "saveExpense error: ${e.message}")
        }
        return null
    }

    override suspend fun saveTask(taskList: List<TaskUpload>) {
        try {
            val taskEntityList = taskList.map { task ->
                task.toEntity()
            }
            room.taskDao().insertTasks(taskEntityList)
        } catch (e: Exception) {
            Log.d(TAG, "saveTask error: ${e.message}")
        }
    }
}