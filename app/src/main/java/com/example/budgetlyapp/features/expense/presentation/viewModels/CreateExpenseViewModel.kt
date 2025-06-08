package com.example.budgetlyapp.features.expense.presentation.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.common.domain.models.ExpenseModel
import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.features.expense.domain.useCase.SaveExpenseUseCase
import com.example.budgetlyapp.features.expense.domain.useCase.VerifyFieldsNewExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val saveExpenseUseCase: SaveExpenseUseCase,
    private val verifyFieldsNewExpenseUseCase: VerifyFieldsNewExpenseUseCase,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    private val _nameExpense = MutableStateFlow("")
    val nameExpense: MutableStateFlow<String> = _nameExpense

    private val _amountExpense = MutableStateFlow("")
    val amountExpense: MutableStateFlow<String> = _amountExpense

    private val _categorySelected = MutableStateFlow(TagModel())
    val categorySelected: MutableStateFlow<TagModel> = _categorySelected

    private val _dayPayString = MutableStateFlow("")
    val dayPayString: MutableStateFlow<String> = _dayPayString

    private val _hasDayPay = MutableStateFlow(true)
    val hasDayPay: MutableStateFlow<Boolean> = _hasDayPay

    private val _hasNotification = MutableStateFlow(false)
    val hasNotification: MutableStateFlow<Boolean> = _hasNotification

    fun onChaneNameExpense(name: String) {
        _nameExpense.value = name
    }

    fun onChaneAmountExpense(amount: String) {
        _amountExpense.value = amount
    }

    fun onChangeCategorySelected(category: TagModel) {
        if (categorySelected.value != category) {
            _categorySelected.value = category
            return
        }
        _categorySelected.value = TagModel()
    }

    fun onChangeDayPayString(day: String) {
        _dayPayString.value = if (day.isEmpty() || day.toInt() in 1..31) {
            day
        } else {
            "31"
        }
    }

    fun onChangeHasDayPay(hasDay: Boolean) {
        _hasDayPay.value = hasDay
    }

    fun onChangeHasNotification(hasNotification: Boolean) {
        _hasNotification.value = hasNotification
    }

    fun onClickSave(navController: NavController) {
        viewModelScope.launch {
            val validateFieldsResult = verifyFieldsNewExpenseUseCase(
                _nameExpense.value,
                _amountExpense.value,
                _categorySelected.value,
                _dayPayString.value,
                _hasDayPay.value
            )

            if (validateFieldsResult.isFailure) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        validateFieldsResult.exceptionOrNull()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            val expenseModel = ExpenseModel(
                expenseName = _nameExpense.value,
                amount = _amountExpense.value.toDouble(),
                tag = _categorySelected.value,
                day = if (!_hasDayPay.value) null else _dayPayString.value.toInt(),
                hasNotification = _hasNotification.value
            )

            val saveExpenseResult = saveExpenseUseCase(expenseModel)

            if (saveExpenseResult.isSuccess) {
                withContext(Dispatchers.Main) {
                    navController.popBackStack()
                }
            }
        }
    }
}