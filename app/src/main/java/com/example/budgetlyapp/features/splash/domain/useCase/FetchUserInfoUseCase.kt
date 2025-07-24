package com.example.budgetlyapp.features.splash.domain.useCase

import android.util.Log
import com.example.budgetlyapp.common.dataStore.DataStoreRepository
import com.example.budgetlyapp.common.dataStore.IncomeValueKey
import com.example.budgetlyapp.common.dataStore.UserNameKey
import com.example.budgetlyapp.features.splash.data.SplashRepository
import javax.inject.Inject

class FetchUserInfoUseCase @Inject constructor(
    private val splashRepository: SplashRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        try {
            val userInfo = splashRepository.fetchUserInfo()

            dataStoreRepository.putUserName(UserNameKey.key, userInfo.userName)
            dataStoreRepository.putIncomeValue(IncomeValueKey.key, userInfo.incomeValue)
        } catch (e: Exception) {
            Log.e("FetchUserInfo", "Error fetching user info", e)
        }
    }
}