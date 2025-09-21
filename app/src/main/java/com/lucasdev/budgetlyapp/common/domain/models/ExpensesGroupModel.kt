package com.lucasdev.budgetlyapp.common.domain.models

data class ExpensesGroupModel(
    val expensesGroupId: String,
    val createdAt: String,
    val expenseList: List<ExpenseModelFromDb>
)
