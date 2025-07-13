package com.example.budgetlyapp.features.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.expense.domain.useCase.DeleteExpenseUseCase
import com.example.budgetlyapp.features.expense.domain.useCase.GetExpenseGroupListUseCase
import com.example.budgetlyapp.features.expense.domain.useCase.UpdateExpenseNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val getExpenseGroupListUseCase: GetExpenseGroupListUseCase,
    private val updateExpenseNotificationUseCase: UpdateExpenseNotificationUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) :
    ViewModel() {
    private val _expenseGroupList = MutableStateFlow<List<ExpensesGroupModel>>(emptyList())
    val expenseGroupList: MutableStateFlow<List<ExpensesGroupModel>> = _expenseGroupList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _showDialog = MutableStateFlow(false)
    val showDialog: MutableStateFlow<Boolean> = _showDialog

    private var expenseGroupIdToDelete: String? = null
    private var expenseIdToDelete: String? = null

    fun getExpenseGroupList() {
        viewModelScope.launch {
            _isLoading.value = true
            _expenseGroupList.value = getExpenseGroupListUseCase().sortedBy { it.createdAt }
            _isLoading.value = false
        }
    }

    fun updateExpenseNotification(
        expenseGroupId: String,
        expenseId: String,
        hasNotification: Boolean
    ) {
        viewModelScope.launch {
            updateExpenseNotificationUseCase(expenseGroupId, expenseId, hasNotification)
        }
    }

    fun showDialog(expenseGroupId: String, expenseId: String) {
        expenseGroupIdToDelete = expenseGroupId
        expenseIdToDelete = expenseId
        _showDialog.value = true
    }

    fun acceptDialog() {
        viewModelScope.launch {
            deleteExpenseUseCase(expenseGroupIdToDelete!!, expenseIdToDelete!!)
            getExpenseGroupList()
        }

        resetDialogDelete()
    }

    fun cancelDialog() {
        resetDialogDelete()
    }

    private fun resetDialogDelete() {
        expenseGroupIdToDelete = null
        expenseIdToDelete = null
        _showDialog.value = false
    }
}