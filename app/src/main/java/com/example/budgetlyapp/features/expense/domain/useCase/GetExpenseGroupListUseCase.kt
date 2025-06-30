package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.expense.data.repository.ExpenseTask
import javax.inject.Inject

class GetExpenseGroupListUseCase @Inject constructor(private val expenseTask: ExpenseTask) {
    suspend operator fun invoke(): List<ExpensesGroupModel> {
        val result = expenseTask.getExpenseGroupList()
        if (result.isFailure) {
            return listOf()
        }

        return result.getOrNull()!!
    }
}