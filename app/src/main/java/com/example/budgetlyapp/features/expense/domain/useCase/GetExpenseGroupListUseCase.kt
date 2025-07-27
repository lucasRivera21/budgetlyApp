package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.example.budgetlyapp.features.expense.data.repository.ExpenseTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseGroupListUseCase @Inject constructor(private val expenseTask: ExpenseTask) {
    suspend operator fun invoke(): Flow<List<ExpenseModelResponse>> {
        return expenseTask.getExpenseGroupList()
    }
}