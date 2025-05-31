package com.example.budgetlyapp.features.register.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.register.domain.model.RegisterUserModel
import com.example.budgetlyapp.common.Util.Companion.MoneyType
import com.example.budgetlyapp.features.register.domain.usecase.RegisterUserUseCase
import com.example.budgetlyapp.navigation.HomeScreen
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
    private val _name = MutableStateFlow("")
    val name = _name

    private val _lastName = MutableStateFlow("")
    val lastName = _lastName

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

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onLastNameChange(newLastName: String) {
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
    private val _incomeValue = MutableStateFlow("")
    val incomeValue = _incomeValue

    private val _moneyType = MutableStateFlow(MoneyType.entries[0].returnMoneyType())
    val moneyType = _moneyType

    fun returnMoneyTypeList(): List<String> {
        return MoneyType.entries.map { it.returnMoneyType() }
    }

    fun onIncomeValueChange(newIncomeValue: String) {
        _incomeValue.value = newIncomeValue
    }

    fun onMoneyTypeChange(newMoneyType: String) {
        _moneyType.value = newMoneyType
    }

    //Account Info

    private val _email = MutableStateFlow("")
    val email = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword

    fun onChangeEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun onChangePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun onChangeConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    //Validate Form
    private fun validateAboutYou(changePage: () -> Unit) {
        if (_name.value.isEmpty() || _lastName.value.isEmpty()) {
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val birthDate =
            LocalDate.of(_yearBirth.value.toInt(), selectedMonthBirth, _dayBirth.value.toInt())

        registerUser.name = _name.value
        registerUser.lastName = _lastName.value
        registerUser.birthDate = birthDate.toString()

        changePage()
    }

    private fun validateIncomingInfo(changePage: () -> Unit) {
        if (_incomeValue.value.isEmpty()) {
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }

        registerUser.incomeValue = _incomeValue.value
        registerUser.moneyType = _moneyType.value

        changePage()
    }

    private fun validateAccountInfo(changePage: () -> Unit, navController: NavController) {
        if (_email.value.isEmpty() || _password.value.isEmpty() || _confirmPassword.value.isEmpty()) {
            Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (_password.value != _confirmPassword.value) {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        changePage()

        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            val resultUser = registerUserUseCase(_email.value, _password.value, registerUser)

            withContext(Dispatchers.Main) {
                if (resultUser.isSuccess) {
                    navController.navigate(HomeScreen.route)
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