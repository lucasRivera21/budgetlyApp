package com.example.budgetlyapp.features.home.domain.models

import com.example.budgetlyapp.common.domain.models.TagModel

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

fun TaskResponse.toNextTaskModel(): NextTaskModel {
    return NextTaskModel(
        taskId = taskId,
        dateDue = dateDue,
        hasDayDue = hasDayDue,
        icon = this.tag.iconId,
        color = this.tag.color,
        taskName = taskName,
        amount = amount
    )
}
