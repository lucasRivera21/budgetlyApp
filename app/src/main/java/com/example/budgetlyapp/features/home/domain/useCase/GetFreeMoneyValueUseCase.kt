package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import javax.inject.Inject

class GetFreeMoneyValueUseCase @Inject constructor() {
    operator fun invoke(
        expenseGroupModelList: List<ExpenseModelResponse>,
        incomeValue: Double
    ): Double {
        var freeMoneyValue = incomeValue
        expenseGroupModelList.forEach { expenseGroupModel ->
            freeMoneyValue -= expenseGroupModel.amount
        }
        return freeMoneyValue
    }
}