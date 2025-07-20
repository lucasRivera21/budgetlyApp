package com.example.budgetlyapp.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetlyapp.common.domain.models.HomeDataModel
import com.example.budgetlyapp.features.home.domain.useCase.FetchHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fetchHomeDataUseCase: FetchHomeDataUseCase) :
    ViewModel() {
    private val _homeData = MutableStateFlow(HomeDataModel())
    val homeData: MutableStateFlow<HomeDataModel> = _homeData

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

            _isLoading.value = false
        }
    }
}