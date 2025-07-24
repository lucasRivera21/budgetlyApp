package com.example.budgetlyapp.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.dataStore.DataStoreRepository
import com.example.budgetlyapp.common.dataStore.IncomeValueKey
import com.example.budgetlyapp.common.dataStore.UserNameKey
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.home.domain.useCase.FetchHomeDataUseCase
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
    private val dataStoreRepository: DataStoreRepository
) :
    ViewModel() {
    private val _expenseGroupList = MutableStateFlow<List<ExpensesGroupModel>>(emptyList())
    val expenseGroupList: MutableStateFlow<List<ExpensesGroupModel>> = _expenseGroupList

    private val _pieList = MutableStateFlow(listOf<Pie>())
    val pieList: MutableStateFlow<List<Pie>> = _pieList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _userName = MutableStateFlow("")
    val userName: MutableStateFlow<String> = _userName

    private val _freeMoney = MutableStateFlow(0.0)
    val freeMoney: MutableStateFlow<Double> = _freeMoney

    private var incomeValue = 0.0

    init {
        getUserName()
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val result = fetchHomeDataUseCase()
            if (result.isSuccess) {
                _expenseGroupList.value = result.getOrNull() ?: emptyList()
            }

            getPieList()

            _isLoading.value = false
        }
    }

    private suspend fun getPieList() {
        try {
            incomeValue = dataStoreRepository.getIncomeValue(IncomeValueKey.key)
            _pieList.value =
                getPieListUseCase(_expenseGroupList.value, incomeValue)
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