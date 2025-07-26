package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.expense.data.repository.ExpenseTask
import javax.inject.Inject

class GetExpenseGroupListUseCase @Inject constructor(private val expenseTask: ExpenseTask) {
    suspend operator fun invoke(): List<ExpensesGroupModel> {
        val expenseModelResponseList = expenseTask.getExpenseGroupList()
        if (expenseModelResponseList.isFailure) {
            return listOf()
        }

        val expenseModelList = expenseModelResponseList.getOrNull()!!
        val expenseModelGroup = expenseModelList.groupBy { it.expenseGroupId }
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

        return expenseModelGroupList
    }
}