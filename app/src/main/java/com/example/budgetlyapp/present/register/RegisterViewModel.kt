package com.example.budgetlyapp.present.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.budgetlyapp.R
import com.example.budgetlyapp.present.register.models.RegisterUserModel
import com.example.budgetlyapp.utils.Util.Companion.MoneyType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val registerUser = RegisterUserModel()

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

    //Validate Form
    private fun validateAboutYou(changePage: () -> Unit) {
        if (_name.value.isEmpty() || _lastName.value.isEmpty()) {
            Toast.makeText(context, "Complete los campos", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(context, "Complete los campos", Toast.LENGTH_SHORT).show()
            return
        }

        registerUser.incomeValue = _incomeValue.value
        registerUser.moneyType = _moneyType.value

        changePage()
    }

    fun validateForm(page: Int, changePage: () -> Unit) {
        when (page) {
            0 -> validateAboutYou(changePage)
            1 -> validateIncomingInfo(changePage)
        }
    }
}