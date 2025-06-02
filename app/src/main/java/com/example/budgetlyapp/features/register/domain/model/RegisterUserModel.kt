package com.example.budgetlyapp.features.register.domain.model

import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel

data class RegisterUserModel(
    var name: String? = null,
    var lastName: String? = null,
    var birthDate: String? = null,
    var incomeValue: String? = null,
    var moneyType: String? = null,
    val expensesGroupList: List<ExpensesGroupModel>? = listOf()
)
