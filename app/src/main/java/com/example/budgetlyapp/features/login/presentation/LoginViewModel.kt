package com.example.budgetlyapp.features.login.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.features.login.domain.LoginUserUseCase
import com.example.budgetlyapp.navigation.HomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    private val _email = MutableStateFlow("")
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: MutableStateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun onChangeEmail(email: String) {
        _email.value = email
    }

    fun onChangePassword(password: String) {
        _password.value = password
    }

    fun loginUser(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val resultLogin = loginUserUseCase(email.value, password.value)
            withContext(Dispatchers.Main) {
                if (resultLogin.isSuccess) {
                    navController.navigate(HomeScreen.route)
                } else {
                    Toast.makeText(context, "Revisa los campos", Toast.LENGTH_SHORT).show()
                }
            }

            _isLoading.value = false
        }
    }
}