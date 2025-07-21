package com.example.budgetlyapp.features.home.domain.useCase

import androidx.compose.ui.graphics.Color
import com.example.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.common.utils.getPercentage
import ir.ehsannarmani.compose_charts.models.Pie
import javax.inject.Inject

class GetPieListUseCase @Inject constructor() {
    operator fun invoke(
        expenseGroupList: List<ExpensesGroupModel>,
        incomeValue: Double
    ): List<Pie> {
        val expenseList: MutableList<ExpenseModelFromDb> = mutableListOf()
        expenseGroupList.forEach {
            expenseList.addAll(it.expenseList)
        }

        val pieList: MutableList<Pie> = mutableListOf()

        expenseList.groupBy { it.tag.tagId }.forEach { (_, expenseList) ->
            val amountSum = expenseList.sumOf { it.amount }
            val color = Color(android.graphics.Color.parseColor(expenseList.first().tag.color))
            pieList.add(
                Pie(
                    label = expenseList.first().tag.tagNameId,
                    data = getPercentage(amountSum, incomeValue),
                    color = color,
                    selectedColor = color.copy(alpha = 0.5f)
                )
            )
        }

        return pieList
    }
}