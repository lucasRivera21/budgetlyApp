package com.lucasdev.budgetlyapp.common.domain.useCases

import com.lucasdev.budgetlyapp.common.data.repository.ExpenseRepository
import com.lucasdev.budgetlyapp.common.domain.models.toExpenseEntity
import com.lucasdev.budgetlyapp.common.utils.UploadState
import javax.inject.Inject

class DownloadExpensesUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke() {
        val expenseRemote = expenseRepository.downloadExpenses()
        val expenseEntityList =
            expenseRemote.map { it.toExpenseEntity(isUpload = UploadState.UPLOADED.code) }
        expenseRepository.insertExpenses(expenseEntityList)
    }
}