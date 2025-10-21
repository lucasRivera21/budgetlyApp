package com.lucasdev.budgetlyapp.common.domain.useCases

import android.util.Log
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.data.repository.ExpenseRepository
import com.lucasdev.budgetlyapp.common.domain.models.toTaskToUpload
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.common.utils.UploadState.Companion.codeToUploadState
import javax.inject.Inject

private const val TAG = "SyncExpensesUseCase"

class SyncExpensesUseCase @Inject constructor(private val repository: ExpenseRepository) {
    suspend operator fun invoke() {
        try {
            uploadExpenses()
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ${e.message}")
        }
    }

    private suspend fun uploadExpenses() {
        val expenseToUploadList = repository.getExpensesToUpload()
        if (expenseToUploadList.isEmpty()) {
            return
        }

        val expenseToUploadGroupByIsUpload = expenseToUploadList.groupBy { it.isUpload }

        for (expenseToUpload in expenseToUploadGroupByIsUpload) {
            val uploadState = codeToUploadState(expenseToUpload.key)
            val expenseList = expenseToUpload.value

            when (uploadState) {
                UploadState.DO_NOT_UPLOAD -> uploadExpensesWithOutUpload(expenseList)
                else -> Unit
            }
        }

        // Upload expenses
    }

    private suspend fun uploadExpensesWithOutUpload(expenseList: List<ExpenseEntity>) {
        expenseList.forEach { expenseEntity ->
            val expenseIdRemote = repository.updateExpenses(expenseEntity)

            if (expenseIdRemote != null) {
                val expenseId = expenseEntity.expenseId

                repository.updateIsUploaded(
                    expenseId = expenseId,
                    expenseIdRemote = expenseIdRemote,
                    isUploadStateCode = UploadState.UPLOADED.code
                )

                val taskList = repository.getTasksToUpload(expenseId)
                if (taskList.isNotEmpty()) {
                    val taskToUploadList = taskList.map { it.toTaskToUpload(expenseIdRemote) }
                    taskToUploadList.forEach { taskToUpload ->
                        val taskIdRemote = repository.updateTask(taskToUpload)
                        taskIdRemote?.let {
                            repository.updateTaskIsUploaded(taskToUpload.taskId, it)
                        }
                    }
                }
            }
        }
    }
}