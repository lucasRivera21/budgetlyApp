package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.example.budgetlyapp.features.home.data.HomeTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHomeDataUseCase @Inject constructor(private val homeTask: HomeTask) {
    suspend operator fun invoke(): Flow<List<ExpenseModelResponse>> {
        return homeTask.fetchHomeData()
    }
}