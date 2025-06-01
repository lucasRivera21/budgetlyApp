package com.example.budgetlyapp.features.main.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budgetlyapp.features.expense.presentation.ExpenseScreen
import com.example.budgetlyapp.features.home.presentation.HomeScreen
import com.example.budgetlyapp.features.profile.presentation.ProfileScreen
import com.example.budgetlyapp.navigation.ExpenseScreen
import com.example.budgetlyapp.navigation.HomeScreen
import com.example.budgetlyapp.navigation.ProfileScreen

@Composable
fun MainNavigation(localNavController: NavHostController) {
    NavHost(
        navController = localNavController,
        startDestination = HomeScreen.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable(ExpenseScreen.route) {
            ExpenseScreen()
        }

        composable(HomeScreen.route) {
            HomeScreen()
        }

        composable(ProfileScreen.route) {
            ProfileScreen()
        }
    }
}