package com.example.budgetlyapp.common.domain.models

data class HomeDataModel(
    var userName: String = "",
    var incomeValue: Double = 0.0,
    var expenseGroupList: List<ExpensesGroupModel> = emptyList()
)
