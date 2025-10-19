package com.lucasdev.budgetlyapp.features.home.domain.models

import com.lucasdev.budgetlyapp.common.domain.models.TagModel

data class TaskResponse(
    val taskId: String,
    val amount: Double,
    val completed: Boolean,
    val createdAt: String,
    val dateDue: String,
    val expenseGroupId: String,
    val expenseId: String,
    val requestCode: Int? = null,
    val hasDayDue: Boolean,
    val hasNotification: Boolean,
    val taskName: String,
    val tag: TagModel
)
