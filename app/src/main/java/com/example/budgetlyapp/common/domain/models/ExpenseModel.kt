package com.example.budgetlyapp.common.domain.models

data class ExpenseModel(
    val expenseName: String,
    val amount: Double,
    val tag: TagModel,
    val day: Int?,
    val hasNotification: Boolean,
)
