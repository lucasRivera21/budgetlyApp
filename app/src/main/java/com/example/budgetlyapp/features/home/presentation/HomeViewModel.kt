package com.example.budgetlyapp.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.domain.models.HomeDataModel
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
    private val getPieListUseCase: GetPieListUseCase
) :
    ViewModel() {
    private val _homeData = MutableStateFlow(HomeDataModel())
    val homeData: MutableStateFlow<HomeDataModel> = _homeData

    private val _pieList = MutableStateFlow(listOf<Pie>())
    val pieList: MutableStateFlow<List<Pie>> = _pieList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            val result = fetchHomeDataUseCase()
            if (result.isSuccess) {
                _homeData.value = result.getOrNull() ?: HomeDataModel()
            }

            getPieList()

            _isLoading.value = false
        }
    }

    private fun getPieList() {
        try {
            _pieList.value =
                getPieListUseCase(_homeData.value.expenseGroupList, _homeData.value.incomeValue)
        } catch (e: Exception) {
            Log.e(TAG, "getPieList: ${e.message}", e)
        }
    }

    fun onClickPie(pieSelected: Pie) {
        val pieIndex = _pieList.value.indexOf(pieSelected)
        _pieList.value =
            _pieList.value.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
    }
}