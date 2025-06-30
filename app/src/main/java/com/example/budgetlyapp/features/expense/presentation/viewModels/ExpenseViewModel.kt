package com.example.budgetlyapp.features.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.expense.domain.useCase.GetExpenseGroupListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(private val getExpenseGroupListUseCase: GetExpenseGroupListUseCase) :
    ViewModel() {
    private val _expenseGroupList = MutableStateFlow<List<ExpensesGroupModel>>(emptyList())
    val expenseGroupList: MutableStateFlow<List<ExpensesGroupModel>> = _expenseGroupList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun getExpenseGroupList() {
        viewModelScope.launch {
            _isLoading.value = true
            _expenseGroupList.value = getExpenseGroupListUseCase()
            _isLoading.value = false
        }
    }
}