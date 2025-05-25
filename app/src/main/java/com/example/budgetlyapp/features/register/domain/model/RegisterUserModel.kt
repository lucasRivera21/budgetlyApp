package com.example.budgetlyapp.features.register.domain.model

data class RegisterUserModel(
    var name: String? = null,
    var lastName: String? = null,
    var birthDate: String? = null,
    var incomeValue: String? = null,
    var moneyType: String? = null
)
