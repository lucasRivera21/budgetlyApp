package com.example.budgetlyapp.features.forgetPassword.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.features.forgetPassword.domain.SendChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val sendChangePasswordUseCase: SendChangePasswordUseCase
) :
    ViewModel() {
    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: MutableStateFlow<TextFieldValue> = _email

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun onChangeEmail(email: TextFieldValue) {
        _email.value = email
    }

    fun back(navController: NavController) {
        navController.popBackStack()
    }

    fun onClickSend(navController: NavController) {
        if (_isLoading.value) return

        _isLoading.value = true

        if (email.value.text.isEmpty()) {
            Toast.makeText(context, "Por favor ingresa un correo electronico", Toast.LENGTH_SHORT)
                .show()

            _isLoading.value = false

            return
        }

        if (!email.value.text.contains("@")) {
            Toast.makeText(
                context,
                "Por favor ingresa un correo electronico valido",
                Toast.LENGTH_SHORT
            )
                .show()

            _isLoading.value = false

            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val wasSend = sendChangePasswordUseCase(email.value.text)

            if (!wasSend) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No se pudo enviar el correo", Toast.LENGTH_SHORT)
                        .show()
                }

                _isLoading.value = false

                return@launch
            }

            _isLoading.value = false

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Correo enviado", Toast.LENGTH_SHORT).show()
                back(navController)
            }
        }
    }
}