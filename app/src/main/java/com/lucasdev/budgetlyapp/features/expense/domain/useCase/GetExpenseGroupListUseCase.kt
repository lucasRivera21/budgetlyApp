package com.lucasdev.budgetlyapp.features.expense.domain.useCase

import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.domain.models.CategoryProvider
import com.lucasdev.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.lucasdev.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.lucasdev.budgetlyapp.features.expense.data.repository.ExpenseTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpenseGroupListUseCase @Inject constructor(private val expenseTask: ExpenseTask) {
    suspend operator fun invoke(): Flow<List<ExpensesGroupModel>> {
        return expenseTask.getExpenseList().map { expenseList ->
            convertExpenseEntityListToExpenseGroupUseCase(expenseList)
        }
    }

    fun convertExpenseEntityListToExpenseGroupUseCase(expenseEntityList: List<ExpenseEntity>): List<ExpensesGroupModel> {
        val expenseModelGroup = expenseEntityList.groupBy { it.expenseGroupId }
        val expenseModelGroupList = expenseModelGroup.map { (expenseGroupId, expenseList) ->
            val minCreatedAtExpense = expenseList.minOf { it.createdAt }
            ExpensesGroupModel(
                expensesGroupId = expenseGroupId,
                createdAt = minCreatedAtExpense,
                expenseList = expenseList.map {
                    val categories = CategoryProvider.categories
                    val tag = categories.find { category -> category.tagId == it.tagId }
                    ExpenseModelFromDb(
                        expenseId = it.expenseId,
                        amount = it.expenseAmount,
                        dayPay = it.day,
                        expenseName = it.expenseName,
                        hasNotification = it.hasNotification,
                        tag = tag!!,
                        createdAt = it.createdAt
                    )
                }
            )
        }

        return expenseModelGroupList
    }
}