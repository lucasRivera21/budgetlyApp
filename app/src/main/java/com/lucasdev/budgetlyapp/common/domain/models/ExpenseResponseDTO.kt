package com.lucasdev.budgetlyapp.common.domain.models

import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity

data class ExpenseResponseDTO(
    val expenseIdRemote: String = "",
    val createdAt: String = "",
    val expenseAmount: Double = 0.0,
    val tagId: Int = 0,
    val expenseGroupId: String = "",
    val hasNotification: Boolean = false,
    val day: Int? = null,
    val expenseName: String = ""
)

fun ExpenseResponseDTO.toExpenseEntity(isUpload: Int) = ExpenseEntity(
    expenseIdRemote = expenseIdRemote,
    expenseName = expenseName,
    expenseAmount = expenseAmount,
    createdAt = createdAt,
    day = day,
    expenseGroupId = expenseGroupId,
    hasNotification = hasNotification,
    tagId = tagId,
    isUpload = isUpload
)
