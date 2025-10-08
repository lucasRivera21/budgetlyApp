package com.lucasdev.budgetlyapp.common.domain.models

import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
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

fun ExpenseModel.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        expenseGroupId = expenseGroupId,
        expenseName = expenseName,
        expenseAmount = amount,
        tagId = tag.tagId,
        day = day,
        hasNotification = hasNotification,
        createdAt = createdAt
    )
}