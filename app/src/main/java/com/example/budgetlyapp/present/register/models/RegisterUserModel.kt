package com.example.budgetlyapp.present.register.models

data class RegisterUserModel(
    var name: String? = null,
    var lastName: String? = null,
    var birthDate: String? = null,
    var incomeValue: String? = null,
    var moneyType: String? = null
)
