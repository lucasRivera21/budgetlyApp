package com.lucasdev.budgetlyapp.common.domain.models

import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity
import com.lucasdev.budgetlyapp.common.utils.UploadState

data class TaskResponseDTO(
    val complete: Boolean = false,
    val createdAt: String = "",
    val dateDue: String = "",
    val expenseId: String = "",
    val taskId: Int = 0,
    val taskIdRemote: String = "",
    val requestCode: Int? = null
)

fun TaskResponseDTO.toTaskEntity(expenseIdLocal: Int) = TaskEntity(
    taskIdRemote = taskIdRemote,
    isComplete = if (!complete) 0 else 1,
    createdAt = createdAt,
    dateDue = dateDue,
    expenseId = expenseIdLocal,
    requestCode = requestCode,
    isUpload = UploadState.UPLOADED.code
)
