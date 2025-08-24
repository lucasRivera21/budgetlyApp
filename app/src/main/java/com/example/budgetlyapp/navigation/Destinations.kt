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

object EditPersonalDataScreen : Destinations {
    override val route: String
        get() = "EditPersonalDataScreen"
}

object ChangePasswordScreen : Destinations {
    override val route: String
        get() = "ChangePasswordScreen"
}

object HomeScreen : Destinations {
    override val route: String
        get() = "HomeScreen"
}

object ExpenseScreen : Destinations {
    override val route: String
        get() = "ExpenseScreen"
}

object CreateExpenseScreen : Destinations {
    override val route: String
        get() = "CreateExpenseScreen"
}

object ProfileScreen : Destinations {
    override val route: String
        get() = "ProfileScreen"
}

object ForgotPasswordScreen : Destinations {
    override val route: String
        get() = "ForgotPasswordScreen"
}