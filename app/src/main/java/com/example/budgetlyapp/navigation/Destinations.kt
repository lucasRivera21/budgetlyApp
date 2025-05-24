package com.example.budgetlyapp.navigation

interface Destinations {
    val route: String
}

object SplashScreen : Destinations {
    override val route: String
        get() = "SplashScreen"
}

object LoginScreen : Destinations {
    override val route: String
        get() = "LoginScreen"
}

object RegisterScreen : Destinations {
    override val route: String
        get() = "RegisterScreen"
}

object MainScreen : Destinations {
    override val route: String
        get() = "MainScreen"
}