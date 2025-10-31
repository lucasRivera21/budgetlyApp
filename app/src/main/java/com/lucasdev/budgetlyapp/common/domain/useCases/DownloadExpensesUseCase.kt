package com.lucasdev.budgetlyapp.common.domain.useCases

import com.lucasdev.budgetlyapp.common.data.repository.ExpenseRepository
import com.lucasdev.budgetlyapp.common.domain.models.toExpenseEntity
import com.lucasdev.budgetlyapp.common.domain.models.toTaskEntity
import com.lucasdev.budgetlyapp.common.utils.UploadState
import javax.inject.Inject

class DownloadExpensesUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke() {
        val expenseRemote = expenseRepository.downloadExpenses()
        expenseRemote.forEach { expenseResponseDTO ->
            val taskList =
                expenseResponseDTO.taskList.filter { it.expenseId == expenseResponseDTO.expenseIdRemote }
            val expenseEntity =
                expenseResponseDTO.toExpenseEntity(isUpload = UploadState.UPLOADED.code)
            expenseRepository.insertExpense(expenseEntity)

            val expenseIdLocal =
                expenseRepository.getExpenseIdLocalByExpenseIdRemote(expenseResponseDTO.expenseIdRemote)

            val taskEntityList = taskList.map { it.toTaskEntity(expenseIdLocal) }
            expenseRepository.insertTask(taskEntityList)
        }
    }
}