package com.lucasdev.budgetlyapp.features.main.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lucasdev.budgetlyapp.features.expense.presentation.ExpenseScreen
import com.lucasdev.budgetlyapp.features.home.presentation.HomeScreen
import com.lucasdev.budgetlyapp.features.profile.presentation.ProfileScreen
import com.lucasdev.budgetlyapp.navigation.ExpenseScreen
import com.lucasdev.budgetlyapp.navigation.HomeScreen
import com.lucasdev.budgetlyapp.navigation.ProfileScreen

@Composable
fun MainNavigation(localNavController: NavHostController, globalNavController: NavHostController) {
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
            ExpenseScreen(globalNavController)
        }

        composable(HomeScreen.route) {
            HomeScreen()
        }

        composable(ProfileScreen.route) {
            ProfileScreen(globalNavController)
        }
    }
}