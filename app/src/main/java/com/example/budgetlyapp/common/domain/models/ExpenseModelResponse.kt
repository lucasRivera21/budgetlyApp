package com.example.budgetlyapp.common.domain.models

data class ExpenseModelResponse(
    val expenseId: String,
    val expenseGroupId: String,
    val amount: Double,
    val dayPay: Int?,
    val expenseName: String,
    val hasNotification: Boolean,
    val tag: TagModel,
    val createdAt: String
)
