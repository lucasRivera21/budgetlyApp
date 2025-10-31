package com.lucasdev.budgetlyapp.common.domain.models

data class TaskToUpload(
    val taskId: Int,
    val expenseId: String,
    val isComplete: Boolean,
    val createdAt: String,
    val requestCode: Int?,
    val dateDue: String
)
