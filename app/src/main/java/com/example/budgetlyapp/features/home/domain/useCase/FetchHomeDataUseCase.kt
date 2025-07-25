package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.home.data.HomeTask
import javax.inject.Inject

class FetchHomeDataUseCase @Inject constructor(private val homeTask: HomeTask) {
    suspend operator fun invoke(): Result<List<ExpensesGroupModel>> {
        return try {
            val result = homeTask.fetchHomeData()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}