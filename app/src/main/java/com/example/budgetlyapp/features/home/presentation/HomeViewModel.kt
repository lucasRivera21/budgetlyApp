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
    init {
        fetchHomeData()
    }

    private val _homeData = MutableStateFlow<HomeDataModel?>(HomeDataModel())
    val homeData: MutableStateFlow<HomeDataModel?> = _homeData

    private fun fetchHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchHomeDataUseCase()
            if (result.isSuccess) {
                _homeData.value = result.getOrNull()
            }
        }
    }
}