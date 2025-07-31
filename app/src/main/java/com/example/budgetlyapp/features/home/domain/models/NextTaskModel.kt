package com.example.budgetlyapp.features.home.domain.models

data class NextTaskModel(
    val taskId: String,
    val dateDue: String,
    val hasDayDue: Boolean,
    val icon: String,
    val color: String,
    val taskName: String,
    val amount: Double
)
