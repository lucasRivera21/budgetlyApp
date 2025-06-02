package com.example.budgetlyapp.common.domain.models

data class ExpensesGroupModel(
    val expensesGroupId: Int,
    val order: Int,
    val expenseList: List<ExpenseModel>,
    val total: Double,
)
