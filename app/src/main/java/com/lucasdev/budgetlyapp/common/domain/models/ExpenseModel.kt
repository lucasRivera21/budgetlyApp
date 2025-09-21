package com.lucasdev.budgetlyapp.common.domain.models

import com.lucasdev.budgetlyapp.common.utils.getTodayDate

data class ExpenseModel(
    val expenseGroupId: String,
    val expenseName: String,
    val amount: Double,
    val tag: TagModel,
    val day: Int?,
    val hasNotification: Boolean,
    val createdAt: String = getTodayDate()
)
