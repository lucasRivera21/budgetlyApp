package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpenseModel
import com.example.budgetlyapp.features.expense.data.repository.CreateExpenseTask
import javax.inject.Inject

class SaveExpenseUseCase @Inject constructor(private val createExpenseTask: CreateExpenseTask) {
    suspend operator fun invoke(expenseModel: ExpenseModel): Result<String> {
        return createExpenseTask.createTask(expenseModel)
    }
}