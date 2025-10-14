package com.lucasdev.budgetlyapp.features.expense.data.dto

import androidx.room.ColumnInfo
import com.lucasdev.budgetlyapp.common.domain.models.CategoryProvider.categories
import com.lucasdev.budgetlyapp.features.expense.domain.models.TaskToUploadNotification

data class TaskToUploadNotificationDTO(
    @ColumnInfo("task_id")
    val taskId: Int,
    @ColumnInfo("request_code")
    val requestCode: Int?,
    @ColumnInfo("tag_id")
    val tagId: Int,
    @ColumnInfo("expense_name")
    val expenseName: String,
    val amount: Double,
    @ColumnInfo("date_due")
    val dateDue: String
)

fun TaskToUploadNotificationDTO.toTaskToUploadNotification(): TaskToUploadNotification {
    return TaskToUploadNotification(
        taskId = taskId,
        requestCode = requestCode,
        iconId = categories[tagId].iconId,
        taskName = expenseName,
        amount = amount,
        dateDue = dateDue

    )
}
