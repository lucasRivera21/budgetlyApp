package com.lucasdev.budgetlyapp.features.expense.domain.models

data class TaskToUploadNotification(
    val taskId: Int,
    val requestCode: Int?,
    val iconId: String,
    val taskName: String,
    val amount: Double,
    val dateDue: String
)