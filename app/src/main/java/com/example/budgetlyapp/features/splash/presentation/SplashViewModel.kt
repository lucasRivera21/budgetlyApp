package com.example.budgetlyapp.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.budgetlyapp.features.splash.domain.useCase.FetchUserInfoUseCase
import com.example.budgetlyapp.navigation.LoginScreen
import com.example.budgetlyapp.navigation.MainScreen
import com.example.budgetlyapp.navigation.SplashScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val fetchUserInfoUseCase: FetchUserInfoUseCase
) : ViewModel() {
    private suspend fun navigate(navController: NavHostController) {
        val destination = if (auth.currentUser != null) MainScreen.route else LoginScreen.route

        withContext(Dispatchers.Main) {
            navController.navigate(destination) {
                popUpTo(SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    fun saveUserInfo(navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchUserInfoUseCase()
            navigate(navController)
        }
    }
}