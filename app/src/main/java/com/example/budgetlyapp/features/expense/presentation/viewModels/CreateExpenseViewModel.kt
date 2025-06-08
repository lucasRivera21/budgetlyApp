package com.example.budgetlyapp.features.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.budgetlyapp.common.domain.models.TagModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor() : ViewModel() {
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

}