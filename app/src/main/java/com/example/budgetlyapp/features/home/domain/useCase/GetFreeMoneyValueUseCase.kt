package com.example.budgetlyapp.features.home.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import javax.inject.Inject

class GetFreeMoneyValueUseCase @Inject constructor() {
    operator fun invoke(
        expenseGroupModelList: List<ExpensesGroupModel>,
        incomeValue: Double
    ): Double {
        var freeMoneyValue = incomeValue
        expenseGroupModelList.forEach { expenseGroupModel ->
            expenseGroupModel.expenseList.forEach { expenseModel ->
                freeMoneyValue -= expenseModel.amount
            }
        }
        return freeMoneyValue
    }
}