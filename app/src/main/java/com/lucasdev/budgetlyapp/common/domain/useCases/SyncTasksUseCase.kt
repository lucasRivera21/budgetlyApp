package com.lucasdev.budgetlyapp.common.domain.useCases

import android.util.Log
import com.lucasdev.budgetlyapp.common.data.repository.TaskWorkerRepository
import com.lucasdev.budgetlyapp.common.domain.models.TaskByUploadStateDTO
import com.lucasdev.budgetlyapp.common.domain.models.toTaskToUpload
import com.lucasdev.budgetlyapp.common.utils.UploadState
import javax.inject.Inject

private const val TAG = "SyncTasksUseCase"

class SyncTasksUseCase @Inject constructor(private val taskRepository: TaskWorkerRepository) {
    suspend operator fun invoke() {
        try {
            val taskList = taskRepository.getTaskList()

            if (taskList.isEmpty()) return

            val taskToUploadGroupedByIsUpload = taskList.groupBy { it.isUploaded }

            for (taskToUpload in taskToUploadGroupedByIsUpload) {
                val isUpload = taskToUpload.key
                val taskListGrouped = taskToUpload.value

                val uploadState = UploadState.codeToUploadState(isUpload)

                when (uploadState) {
                    UploadState.DO_NOT_UPLOAD -> {
                        uploadTasksWithOutUpload(taskListGrouped)
                    }

                    else -> Unit
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing tasks: ${e.message}")
        }
    }

    private suspend fun uploadTasksWithOutUpload(taskList: List<TaskByUploadStateDTO>) {
        taskList.forEach { taskByUploadStateDTO ->
            val taskToUpload = taskByUploadStateDTO.toTaskToUpload()
            val taskIdRemote = taskRepository.uploadTask(taskToUpload)

            if (taskIdRemote != null) {
                taskRepository.updateTaskIsUploaded(taskByUploadStateDTO.taskId, taskIdRemote)
            }
        }
    }
}