package com.example.budgetlyapp.features.profile.presentation.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.utils.clearThousandFormat
import com.example.budgetlyapp.common.utils.formatThousand
import com.example.budgetlyapp.features.profile.domain.useCase.UpdateIncomeValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditPersonalDataViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val updateIncomeValueUseCase: UpdateIncomeValueUseCase
) :
    ViewModel() {
    private val _incomeValue = MutableStateFlow(TextFieldValue())
    val incomeValue: MutableStateFlow<TextFieldValue> = _incomeValue

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun onChangeIncomeValue(value: TextFieldValue) {
        val newValue = if (value.text.isNotBlank()) {
            value.text
                .clearThousandFormat()
                .toDouble()
                .formatThousand()
        } else value.text

        _incomeValue.value = value.copy(
            text = newValue,
            selection = TextRange(newValue.length)
        )
    }

    fun onBack(navController: NavHostController) {
        navController.popBackStack()
    }

    fun saveIncomeValueChange(navController: NavHostController) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            if (_incomeValue.value.text.isEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        getString(context, R.string.error_value_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                _isLoading.value = false
                return@launch
            }

            val incomeValueString = _incomeValue.value.text.replace(",", "")
            val updateIncomeResult = updateIncomeValueUseCase(incomeValueString)

            if (updateIncomeResult.isFailure) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        updateIncomeResult.exceptionOrNull()!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                _isLoading.value = false
                return@launch
            }
            _isLoading.value = false
            withContext(Dispatchers.Main) {
                onBack(navController)
            }
        }
    }
}