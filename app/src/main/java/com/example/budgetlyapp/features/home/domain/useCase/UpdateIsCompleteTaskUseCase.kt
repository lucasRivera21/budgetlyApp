package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.features.home.data.HomeTask
import javax.inject.Inject

class UpdateIsCompleteTaskUseCase @Inject constructor(private val homeTask: HomeTask) {
    suspend operator fun invoke(taskId: String) {
        homeTask.updateIsCompleteTask(taskId)
    }
}