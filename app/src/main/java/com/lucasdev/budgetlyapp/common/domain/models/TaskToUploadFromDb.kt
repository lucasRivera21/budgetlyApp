package com.lucasdev.budgetlyapp.common.domain.models

import androidx.room.ColumnInfo

data class TaskToUploadFromDb(
    @ColumnInfo("task_id")
    val taskId: Int,
    @ColumnInfo("is_complete")
    val isComplete: Boolean,
    @ColumnInfo("created_at")
    val createdAt: String,
    @ColumnInfo("date_due")
    val dateDue: String,
)

fun TaskToUploadFromDb.toTaskToUpload(expenseId: String): TaskToUpload {
    return TaskToUpload(
        taskId = taskId,
        expenseId = expenseId,
        isComplete = isComplete,
        createdAt = createdAt,
        dateDue = dateDue
    )
}
