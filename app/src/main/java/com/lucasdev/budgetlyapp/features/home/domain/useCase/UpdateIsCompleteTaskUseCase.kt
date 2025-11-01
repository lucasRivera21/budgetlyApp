package com.lucasdev.budgetlyapp.features.home.domain.useCase

import com.lucasdev.budgetlyapp.alarm.AlarmScheduler
import com.lucasdev.budgetlyapp.common.utils.UploadState
import com.lucasdev.budgetlyapp.features.home.data.HomeTask
import javax.inject.Inject

class UpdateIsCompleteTaskUseCase @Inject constructor(
    private val homeTask: HomeTask,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(taskId: String) {
        val requestCode = homeTask.fetchRequestCode(taskId)
        if (requestCode != null) {
            alarmScheduler.cancel(requestCode)
        }

        val uploadCode = homeTask.getUploadByTaskId(taskId.toInt())
        val uploadState = UploadState.codeToUploadState(uploadCode)

        val uploadStateToUpdate =
            if (uploadState == UploadState.DO_NOT_UPLOAD) UploadState.DO_NOT_UPLOAD else UploadState.EDITED

        homeTask.updateIsCompleteTask(taskId, uploadStateToUpdate)
    }
}