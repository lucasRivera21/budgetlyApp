package com.example.budgetlyapp.navigation

interface Destinations {
    val route: String
}

object SplashScreen : Destinations {
    override val route: String
        get() = "SplashScreen"
}