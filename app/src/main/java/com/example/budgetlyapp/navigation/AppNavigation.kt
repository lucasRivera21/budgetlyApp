package com.example.budgetlyapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetlyapp.SplashScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route
    ) {
        composable(SplashScreen.route) {
            SplashScreen(navController, innerPadding)
        }
    }
}