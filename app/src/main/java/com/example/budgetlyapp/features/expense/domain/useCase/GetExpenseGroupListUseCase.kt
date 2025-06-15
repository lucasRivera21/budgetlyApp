package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.features.expense.data.repository.ExpenseTask
import javax.inject.Inject

class GetExpenseGroupListUseCase @Inject constructor(private val expenseTask: ExpenseTask) {
    suspend operator fun invoke() {
        expenseTask.getExpenseGroupList()
    }
}