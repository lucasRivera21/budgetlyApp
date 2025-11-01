package com.lucasdev.budgetlyapp.common.domain.models

import androidx.room.ColumnInfo

data class TaskByUploadStateDTO(
    @ColumnInfo(name = "expense_id_remote")
    val expenseIdRemote: String?,
    @ColumnInfo("task_id")
    val taskId: Int,
    @ColumnInfo("task_id_remote")
    val taskIdRemote: String?,
    @ColumnInfo("is_complete")
    val isComplete: Boolean,
    @ColumnInfo("created_at")
    val createdAt: String,
    @ColumnInfo("date_due")
    val dateDue: String,
    @ColumnInfo("request_code")
    val requestCode: Int?,
    @ColumnInfo("is_upload")
    val isUploaded: Int
)

fun TaskByUploadStateDTO.toTaskToUpload() = TaskToUpload(
    taskId = taskId,
    expenseId = expenseIdRemote!!,
    isComplete = isComplete,
    createdAt = createdAt,
    requestCode = requestCode,
    dateDue = dateDue
)
