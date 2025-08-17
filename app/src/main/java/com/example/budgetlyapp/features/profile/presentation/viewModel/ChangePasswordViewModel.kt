package com.example.budgetlyapp.features.profile.presentation.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.profile.domain.useCase.ChangePasswordUseCase
import com.example.budgetlyapp.features.profile.domain.useCase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) :
    ViewModel() {
    private val _currentPassword = MutableStateFlow(TextFieldValue())
    val currentPassword: MutableStateFlow<TextFieldValue> = _currentPassword

    private val _newPassword = MutableStateFlow(TextFieldValue())
    val newPassword: MutableStateFlow<TextFieldValue> = _newPassword

    private val _confirmPassword = MutableStateFlow(TextFieldValue())
    val confirmPassword: MutableStateFlow<TextFieldValue> = _confirmPassword

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun onCurrentPasswordChange(value: TextFieldValue) {
        _currentPassword.value = value
    }

    fun onNewPasswordChange(value: TextFieldValue) {
        _newPassword.value = value
    }

    fun onConfirmPasswordChange(value: TextFieldValue) {
        _confirmPassword.value = value
    }

    fun savePassword(navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val validateResult = validatePasswordUseCase(
                _currentPassword.value.text,
                _newPassword.value.text,
                _confirmPassword.value.text
            )

            if (validateResult.isFailure) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        validateResult.exceptionOrNull()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                _isLoading.value = false
                return@launch
            }

            val changeResult = changePasswordUseCase(_newPassword.value.text)
            if (changeResult.isFailure) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        changeResult.exceptionOrNull()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                _isLoading.value = false
                return@launch
            }

            _isLoading.value = false
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    getString(context, R.string.profile_password_updated),
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            }
        }
    }
}