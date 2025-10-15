package com.lucasdev.budgetlyapp.features.home.domain.useCase

import com.lucasdev.budgetlyapp.common.domain.models.CategoryProvider
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.lucasdev.budgetlyapp.features.home.data.HomeTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchHomeDataUseCase @Inject constructor(private val homeTask: HomeTask) {
    suspend operator fun invoke(): Flow<List<ExpenseModelResponse>> {
        return homeTask.getHomeData().map { expenseList ->
            expenseList.map {
                ExpenseModelResponse(
                    expenseId = it.expenseId.toString(),
                    expenseGroupId = it.expenseGroupId,
                    amount = it.expenseAmount,
                    dayPay = it.day,
                    expenseName = it.expenseName,
                    hasNotification = it.hasNotification,
                    tag = CategoryProvider.getCategoryById(it.tagId),
                    createdAt = it.createdAt
                )
            }
        }
    }
}