package com.example.budgetlyapp.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.dataStore.DataStoreRepository
import com.example.budgetlyapp.common.dataStore.IncomeValueKey
import com.example.budgetlyapp.common.dataStore.UserNameKey
import com.example.budgetlyapp.common.domain.models.ExpenseModelResponse
import com.example.budgetlyapp.features.home.domain.models.NextTaskModel
import com.example.budgetlyapp.features.home.domain.models.toNextTaskModel
import com.example.budgetlyapp.features.home.domain.useCase.FetchHomeDataUseCase
import com.example.budgetlyapp.features.home.domain.useCase.FetchNextExpensesUseCase
import com.example.budgetlyapp.features.home.domain.useCase.GetFreeMoneyValueUseCase
import com.example.budgetlyapp.features.home.domain.useCase.GetPieListUseCase
import com.example.budgetlyapp.features.home.domain.useCase.UpdateIsCompleteTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ehsannarmani.compose_charts.models.Pie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchHomeDataUseCase: FetchHomeDataUseCase,
    private val getPieListUseCase: GetPieListUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val getFreeMoneyValueUseCase: GetFreeMoneyValueUseCase,
    private val fetchNextExpensesUseCase: FetchNextExpensesUseCase,
    private val updateIsCompleteTaskUseCase: UpdateIsCompleteTaskUseCase
) :
    ViewModel() {
    private val _pieList = MutableStateFlow(listOf<Pie>())
    val pieList: MutableStateFlow<List<Pie>> = _pieList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _userName = MutableStateFlow("")
    val userName: MutableStateFlow<String> = _userName

    private val _freeMoneyValue = MutableStateFlow(0.0)
    val freeMoneyValue: MutableStateFlow<Double> = _freeMoneyValue

    private val _nextTaskList = MutableStateFlow(listOf<NextTaskModel>())
    val nextTaskList: MutableStateFlow<List<NextTaskModel>> = _nextTaskList

    private var incomeValue = 0.0

    init {
        getUserName()
        fetchHomeData()
        fetchNextExpenses()
    }

    private fun fetchNextExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchNextExpensesUseCase().collect { taskResponseList ->
                _nextTaskList.value =
                    taskResponseList.map { it.toNextTaskModel() }.sortedBy { it.dateDue }
            }
        }
    }

    private fun fetchHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            fetchHomeDataUseCase().collect { expenseModelResponseList ->
                incomeValue = dataStoreRepository.getDouble(IncomeValueKey.key)
                _freeMoneyValue.value =
                    getFreeMoneyValueUseCase(expenseModelResponseList, incomeValue)

                getPieList(expenseModelResponseList)

                _isLoading.value = false
            }
        }
    }

    private fun getPieList(expenseModelResponseList: List<ExpenseModelResponse>) {
        try {
            _pieList.value =
                getPieListUseCase(expenseModelResponseList, incomeValue)
        } catch (e: Exception) {
            Log.e(TAG, "getPieList: ${e.message}", e)
        }
    }

    private fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _userName.value = dataStoreRepository.getString(UserNameKey.key)
        }
    }

    fun updateIsCompleteTask(taskId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateIsCompleteTaskUseCase(taskId)
        }
    }

    fun onClickPie(pieSelected: Pie) {
        val pieIndex = _pieList.value.indexOf(pieSelected)
        _pieList.value =
            _pieList.value.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
    }
}