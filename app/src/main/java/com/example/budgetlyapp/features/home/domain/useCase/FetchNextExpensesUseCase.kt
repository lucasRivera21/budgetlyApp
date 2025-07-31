package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.features.home.data.HomeTask
import com.example.budgetlyapp.features.home.domain.models.TaskResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchNextExpensesUseCase @Inject constructor(private val homeTask: HomeTask) {
    suspend operator fun invoke(): Flow<List<TaskResponse>> = homeTask.fetchNextExpensesUseCase()
}