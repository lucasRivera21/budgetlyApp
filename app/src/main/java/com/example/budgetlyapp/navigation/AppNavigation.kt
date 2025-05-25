package com.example.budgetlyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetlyapp.features.home.presentation.HomeScreen
import com.example.budgetlyapp.features.splash.presentation.SplashScreen
import com.example.budgetlyapp.features.login.presentation.LoginScreen
import com.example.budgetlyapp.features.register.presentation.RegisterScreen

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

        composable(HomeScreen.route) {
            HomeScreen()
        }
    }
}