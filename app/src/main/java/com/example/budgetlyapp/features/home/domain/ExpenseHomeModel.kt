package com.example.budgetlyapp.features.home.domain

import androidx.compose.ui.graphics.Color

data class ExpenseHomeModel(
    val expenseId: Int,
    val expenseDate: String,
    val expenseState: String,
    val icon: Int,
    val color: Color,
    val expenseName: String,
    val amount: String
)
