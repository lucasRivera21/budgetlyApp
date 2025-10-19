package com.lucasdev.budgetlyapp.features.home.data

import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.domain.models.TagModel

data class ExpenseApiDTO(
    val amount: Double,
    val createdAt: String,
    val day: Int?,
    val expenseGroupId: String,
    val expenseName: String,
    val hasNotification: Boolean,
    val tag: TagModel
)

fun ExpenseApiDTO.toExepenseEntity() = ExpenseEntity(
    expenseAmount = amount,
    createdAt = createdAt,
    day = day,
    expenseGroupId = expenseGroupId,
    expenseName = expenseName,
    hasNotification = hasNotification,
    tagId = tag.tagId,
    isUpload = 1
)
