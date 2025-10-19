package com.lucasdev.budgetlyapp.features.home.data

import com.lucasdev.budgetlyapp.common.domain.models.CategoryProvider.getCategoryById
import com.lucasdev.budgetlyapp.features.home.domain.models.NextTaskModel
import com.lucasdev.budgetlyapp.features.home.domain.models.TaskResponse

data class NextExpenseDTO(
    val taskId: Int,
    val amount: Double,
    val isCompleted: Boolean,
    val createdAt: String,
    val dateDue: String,
    val expenseGroupId: String,
    val expenseId: String,
    val requestCode: Int? = null,
    val dayDue: Int? = null,
    val hasNotification: Boolean,
    val expenseName: String,
    val tagId: Int
)

fun NextExpenseDTO.toTaskResponse(): TaskResponse {
    return TaskResponse(
        taskId = taskId.toString(),
        amount = amount,
        completed = isCompleted,
        createdAt = createdAt,
        dateDue = dateDue,
        expenseGroupId = expenseGroupId,
        expenseId = expenseId,

        requestCode = requestCode,
        hasDayDue = dayDue != null,
        hasNotification = hasNotification,
        taskName = expenseName,
        tag = getCategoryById(tagId)
    )
}

fun NextExpenseDTO.toNextTaskModel(): NextTaskModel {
    val tag = getCategoryById(tagId)
    return NextTaskModel(
        taskId = taskId.toString(),
        dateDue = dateDue,
        hasDayDue = dayDue != null,
        icon = tag.iconId,
        color = tag.color,
        taskName = expenseName,
        amount = amount
    )
}
