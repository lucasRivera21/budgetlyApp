package com.example.budgetlyapp.features.expense.domain.models

data class TaskToUploadNotificationResponse(
    val requestCode: Int?,
    val iconId: String,
    val taskName: String,
    val amount: Double,
    val dateDue: String
)