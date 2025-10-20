package com.lucasdev.budgetlyapp.common.domain.models

data class ExpenseToUpload(
    val expenseId: Int,
    val expenseName: String,
    val expenseAmount: Double,
    val createdAt: String,
    val day: Int?,
    val expenseGroupId: String,
    val hasNotification: Boolean,
    val tagId: Int
)
