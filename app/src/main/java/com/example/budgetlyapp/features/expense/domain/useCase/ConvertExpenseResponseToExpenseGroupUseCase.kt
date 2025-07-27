package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import javax.inject.Inject

class ConvertExpenseResponseToExpenseGroupUseCase @Inject constructor() {
    operator fun invoke(expenseModelResponseList: List<ExpenseModelResponse>): List<ExpensesGroupModel> {
        val expenseModelGroup = expenseModelResponseList.groupBy { it.expenseGroupId }
        val expenseModelGroupList = expenseModelGroup.map { (expenseGroupId, expenseList) ->
            val minCreatedAtExpense = expenseList.minOf { it.createdAt }
            ExpensesGroupModel(
                expensesGroupId = expenseGroupId,
                createdAt = minCreatedAtExpense,
                expenseList = expenseList.map {
                    ExpenseModelFromDb(
                        expenseId = it.expenseId,
                        amount = it.amount,
                        dayPay = it.dayPay,
                        expenseName = it.expenseName,
                        hasNotification = it.hasNotification,
                        tag = it.tag,
                        createdAt = it.createdAt
                    )
                }
            )
        }

        return expenseModelGroupList.sortedBy { it.createdAt }
    }
}