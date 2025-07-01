package com.example.budgetlyapp.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.budgetlyapp.features.expense.presentation.CreateExpenseScreen
import com.example.budgetlyapp.features.splash.presentation.SplashScreen
import com.example.budgetlyapp.features.login.presentation.LoginScreen
import com.example.budgetlyapp.features.main.presentation.MainScreen
import com.example.budgetlyapp.features.register.presentation.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it })
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it })
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it })
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it })
        }
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
            MainScreen(navController)
        }

        composable(
            "${CreateExpenseScreen.route}/{expenseGroupId}",
            arguments = listOf(navArgument("expenseGroupId") {
                type = NavType.StringType
            })
        ) {
            val expenseGroupId = it.arguments?.getString("expenseGroupId")
            CreateExpenseScreen(expenseGroupId, navController)
        }
    }
}