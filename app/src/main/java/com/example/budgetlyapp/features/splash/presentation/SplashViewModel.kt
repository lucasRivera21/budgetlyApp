package com.example.budgetlyapp.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.budgetlyapp.navigation.LoginScreen
import com.example.budgetlyapp.navigation.MainScreen
import com.example.budgetlyapp.navigation.SplashScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {
    fun navigate(navController: NavHostController) {
        if (auth.currentUser != null) {
            navController.navigate(MainScreen.route) {
                popUpTo(SplashScreen.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(LoginScreen.route) {
                popUpTo(SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}