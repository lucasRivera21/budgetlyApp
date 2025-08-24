package com.example.budgetlyapp.features.register.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.register.domain.model.RegisterUserModel
import com.example.budgetlyapp.common.utils.MoneyType
import com.example.budgetlyapp.common.utils.clearThousandFormat
import com.example.budgetlyapp.common.utils.formatThousand
import com.example.budgetlyapp.features.register.domain.usecase.RegisterUserUseCase
import com.example.budgetlyapp.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

const val TAG = "RegisterViewModel"

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerUserUseCase: RegisterUserUseCase
) :
    ViewModel() {
    private val registerUser = RegisterUserModel()

    private val isLoading = MutableStateFlow(false)
    val isLoadingFlow: MutableStateFlow<Boolean> = isLoading

    //About You
    private val _name = MutableStateFlow(TextFieldValue(""))
    val name: MutableStateFlow<TextFieldValue> = _name

    private val _lastName = MutableStateFlow(TextFieldValue(""))
    val lastName: MutableStateFlow<TextFieldValue> = _lastName

    private val _dayBirth = MutableStateFlow("1")
    val dayBirth = _dayBirth

    private val _monthBirth = MutableStateFlow("")
    val monthBirth = _monthBirth

    private var selectedMonthBirth = 1

    private val _yearBirth = MutableStateFlow(LocalDate.now().year.toString())
    val yearBirth = _yearBirth

    fun returnMonthList(): List<Int> {
        return listOf(
            R.string.register_month_jan,
            R.string.register_month_feb,
            R.string.register_month_mar,
            R.string.register_month_abr,
            R.string.register_month_may,
            R.string.register_month_jun,
            R.string.register_month_jul,
            R.string.register_month_aug,
            R.string.register_month_sep,
            R.string.register_month_oct,
            R.string.register_month_nov,
            R.string.register_month_dic
        )
    }

    fun returnDayList(): List<Int> {
        return (1..31).toList()
    }

    fun returnYearList(): List<Int> {
        val currentYear = LocalDate.now().year
        return (currentYear - 100..currentYear).toList().reversed()
    }

    fun onNameChange(newName: TextFieldValue) {
        _name.value = newName
    }

    fun onLastNameChange(newLastName: TextFieldValue) {
        _lastName.value = newLastName
    }

    fun onDayBirthChange(newDayBirth: String) {
        _dayBirth.value = newDayBirth
    }

    fun onSelectedMonthBirthChange(newSelectedMonthBirth: Int) {
        selectedMonthBirth = newSelectedMonthBirth + 1
    }

    fun onMonthBirthChange(newMonthBirth: String) {
        _monthBirth.value = newMonthBirth
    }

    fun onYearBirthChange(newYearBirth: String) {
        _yearBirth.value = newYearBirth
    }

    //Incoming Info
    private val _incomeValue = MutableStateFlow(TextFieldValue(""))
    val incomeValue = _incomeValue

    private val _moneyType = MutableStateFlow(MoneyType.entries[0].returnMoneyType())
    val moneyType = _moneyType

    fun returnMoneyTypeList(): List<String> {
        return MoneyType.entries.map { it.returnMoneyType() }
    }

    fun onIncomeValueChange(newIncomeValue: TextFieldValue) {
        val newValue = if (newIncomeValue.text.isNotBlank()) {
            newIncomeValue.text.clearThousandFormat().toDouble().formatThousand()
        } else newIncomeValue.text
        _incomeValue.value =
            newIncomeValue.copy(text = newValue, selection = TextRange(newValue.length))
    }

    fun onMoneyTypeChange(newMoneyType: String) {
        _moneyType.value = newMoneyType
    }

    //Account Info

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: MutableStateFlow<TextFieldValue> = _email

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password: MutableStateFlow<TextFieldValue> = _password

    private val _confirmPassword = MutableStateFlow(TextFieldValue(""))
    val confirmPassword: MutableStateFlow<TextFieldValue> = _confirmPassword

    fun onChangeEmail(newEmail: TextFieldValue) {
        _email.value = newEmail
    }

    fun onChangePassword(newPassword: TextFieldValue) {
        _password.value = newPassword
    }

    fun onChangeConfirmPassword(newConfirmPassword: TextFieldValue) {
        _confirmPassword.value = newConfirmPassword
    }

    //Validate Form
    private fun validateAboutYou(changePage: () -> Unit) {
        if (_name.value.text.isEmpty() || _lastName.value.text.isEmpty()) {
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val birthDate =
            LocalDate.of(_yearBirth.value.toInt(), selectedMonthBirth, _dayBirth.value.toInt())

        registerUser.name = _name.value.text
        registerUser.lastName = _lastName.value.text
        registerUser.birthDate = birthDate.toString()

        changePage()
    }

    private fun validateIncomingInfo(changePage: () -> Unit) {
        if (_incomeValue.value.text.isEmpty()) {
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }

        registerUser.incomeValue = _incomeValue.value.text.replace(",", "")
        registerUser.moneyType = _moneyType.value

        changePage()
    }

    private fun validateAccountInfo(changePage: () -> Unit, navController: NavController) {
        if (_email.value.text.isEmpty() || _password.value.text.isEmpty() || _confirmPassword.value.text.isEmpty()) {
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!_email.value.text.contains("@")) {
            Toast.makeText(context, "El email no es válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (_password.value.text.length < 8) {
            Toast.makeText(
                context,
                "La contraseña debe tener al menos 8 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (_password.value != _confirmPassword.value) {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        changePage()

        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            val resultUser =
                registerUserUseCase(_email.value.text, _password.value.text, registerUser)

            withContext(Dispatchers.Main) {
                if (resultUser.isSuccess) {
                    navController.navigate(MainScreen.route)
                } else {
                    Log.d(TAG, "Error: ${resultUser.exceptionOrNull()}")
                    Toast.makeText(
                        context,
                        "Error al registrarse",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            isLoading.value = false
        }
    }

    fun validateForm(page: Int, navController: NavController, changePage: () -> Unit) {
        when (page) {
            0 -> validateAboutYou(changePage)
            1 -> validateIncomingInfo(changePage)
            2 -> validateAccountInfo(changePage, navController)
        }
    }
}