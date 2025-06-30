package com.example.budgetlyapp.common.domain.models

data class ExpenseModelFromDb(
    val expenseId: String,
    val amount: Double,
    val dayPay: Int,
    val expenseName: String,
    val hasNotification: Boolean,
    val tag: TagModel
)
