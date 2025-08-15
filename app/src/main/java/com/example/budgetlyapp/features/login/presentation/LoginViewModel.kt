package com.example.budgetlyapp.features.login.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.features.login.domain.LoginUserUseCase
import com.example.budgetlyapp.features.splash.domain.useCase.FetchUserInfoUseCase
import com.example.budgetlyapp.navigation.MainScreen
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
    @ApplicationContext private val context: Context,
    private val fetchUserInfoUseCase: FetchUserInfoUseCase
) :
    ViewModel() {
    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: MutableStateFlow<TextFieldValue> = _email

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password: MutableStateFlow<TextFieldValue> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun onChangeEmail(email: TextFieldValue) {
        _email.value = email
    }

    fun onChangePassword(password: TextFieldValue) {
        _password.value = password
    }

    fun loginUser(navController: NavController) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val resultLogin = loginUserUseCase(email.value.text, password.value.text)
            if (resultLogin.isSuccess) {
                fetchUserInfoUseCase()
            }
            withContext(Dispatchers.Main) {
                if (resultLogin.isSuccess) {
                    navController.navigate(MainScreen.route)
                } else {
                    Toast.makeText(context, "Revisa los campos", Toast.LENGTH_SHORT).show()
                }
            }

            _isLoading.value = false
        }
    }
}