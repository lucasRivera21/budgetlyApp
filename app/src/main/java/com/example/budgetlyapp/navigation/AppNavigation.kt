package com.example.budgetlyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetlyapp.present.splash.SplashScreen
import com.example.budgetlyapp.present.login.LoginScreen
import com.example.budgetlyapp.present.main.MainScreen
import com.example.budgetlyapp.present.register.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route
    ) {
        composable(SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(RegisterScreen.route) {
            RegisterScreen(navController)
        }

        composable(MainScreen.route) {
            MainScreen()
        }
    }
}