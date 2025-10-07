package com.lucasdev.budgetlyapp.features.expense.domain.models

import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity
import com.lucasdev.budgetlyapp.common.domain.models.TagModel
import com.lucasdev.budgetlyapp.common.utils.getTodayDate

data class TaskUpload(
    val taskName: String,
    val expenseId: Int,
    val expenseGroupId: String,
    val requestCode: Int?,
    val dateDue: String?,
    val hasDayDue: Boolean,
    val createdAt: String = getTodayDate(),
    val tag: TagModel,
    val amount: Double,
    val hasNotification: Boolean,
    val isCompleted: Boolean = false
)

fun TaskUpload.toEntity(): TaskEntity {
    return TaskEntity(
        createdAt = this.createdAt,
        dateDue = this.dateDue,
        expenseId = this.expenseId,
        requestCode = this.requestCode
    )
}
