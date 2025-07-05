package com.example.budgetlyapp.common.domain.models

import com.example.budgetlyapp.common.utils.getTodayDate

data class ExpenseModel(
    val expenseName: String,
    val amount: Double,
    val tag: TagModel,
    val day: Int?,
    val hasNotification: Boolean,
    val createdAt: String = getTodayDate()
)
