package com.example.budgetlyapp.features.expense.domain.models

import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.common.utils.getTodayDate

data class TaskUpload(
    val taskName: String,
    val expenseId: String,
    val expenseGroupId: String,
    val requestCode: Int?,
    val dateDue: String?,
    val hasDayDue: Boolean,
    val createdAt: String = getTodayDate(),
    val tag: TagModel,
    val amount: Double,
    val hasNotification: Boolean,
    val isCompleted: Boolean = false
)
