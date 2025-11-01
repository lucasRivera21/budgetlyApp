package com.lucasdev.budgetlyapp.common.domain.useCases

import android.util.Log
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.data.repository.ExpenseRepository
import com.lucasdev.budgetlyapp.common.data.repository.TaskWorkerRepository
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.common.utils.UploadState.Companion.codeToUploadState
import javax.inject.Inject

private const val TAG = "SyncExpensesUseCase"

class SyncExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository,
    private val taskRepository: TaskWorkerRepository
) {
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
                UploadState.DELETED -> deleteExpenses(expenseList)
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
            }
        }
    }

    private suspend fun deleteExpenses(expenseList: List<ExpenseEntity>) {
        expenseList.forEach { expenseEntity ->
            val taskIdRemoteList = taskRepository.getTaskStringList(expenseEntity.expenseId)

            repository.deleteExpenseFromApi(expenseEntity.expenseIdRemote, taskIdRemoteList)
            repository.deleteExpenseFromDb(expenseEntity.expenseId)
        }
    }
}