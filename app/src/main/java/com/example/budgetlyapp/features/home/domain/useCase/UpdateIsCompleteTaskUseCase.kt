package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.alarm.AlarmScheduler
import com.example.budgetlyapp.features.home.data.HomeTask
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
        homeTask.updateIsCompleteTask(taskId)
    }
}