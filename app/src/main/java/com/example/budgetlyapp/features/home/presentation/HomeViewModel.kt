package com.example.budgetlyapp.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.dataStore.DataStoreRepository
import com.example.budgetlyapp.common.dataStore.IncomeValueKey
import com.example.budgetlyapp.common.dataStore.UserNameKey
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.home.domain.useCase.FetchHomeDataUseCase
import com.example.budgetlyapp.features.home.domain.useCase.GetFreeMoneyValueUseCase
import com.example.budgetlyapp.features.home.domain.useCase.GetPieListUseCase
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
    private val getFreeMoneyValueUseCase: GetFreeMoneyValueUseCase
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

    private var incomeValue = 0.0
    private var expenseGroupList = listOf<ExpensesGroupModel>()

    init {
        getUserName()
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val result = fetchHomeDataUseCase()
            if (result.isSuccess) {
                expenseGroupList = result.getOrNull() ?: emptyList()
            }

            incomeValue = dataStoreRepository.getIncomeValue(IncomeValueKey.key)
            _freeMoneyValue.value = getFreeMoneyValueUseCase(expenseGroupList, incomeValue)

            getPieList()

            _isLoading.value = false
        }
    }

    private fun getPieList() {
        try {
            _pieList.value =
                getPieListUseCase(expenseGroupList, incomeValue)
        } catch (e: Exception) {
            Log.e(TAG, "getPieList: ${e.message}", e)
        }
    }

    private fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _userName.value = dataStoreRepository.getUserName(UserNameKey.key)
        }
    }

    fun onClickPie(pieSelected: Pie) {
        val pieIndex = _pieList.value.indexOf(pieSelected)
        _pieList.value =
            _pieList.value.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
    }
}