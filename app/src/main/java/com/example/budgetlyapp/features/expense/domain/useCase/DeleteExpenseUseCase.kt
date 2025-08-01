package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.features.expense.data.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend operator fun invoke(expenseId: String) {
        expenseRepository.deleteExpense(expenseId)
    }
}