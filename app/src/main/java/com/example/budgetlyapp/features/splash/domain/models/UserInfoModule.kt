package com.example.budgetlyapp.features.splash.domain.models

data class UserInfoModule(
    val email: String = "",
    val userName: String = "",
    val userLastName: String = "",
    val incomeValue: Double = 0.0
)
