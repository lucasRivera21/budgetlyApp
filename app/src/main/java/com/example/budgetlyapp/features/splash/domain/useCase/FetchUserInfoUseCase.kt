package com.example.budgetlyapp.features.splash.domain.useCase

import android.util.Log
import com.example.budgetlyapp.common.dataStore.DataStoreRepository
import com.example.budgetlyapp.common.dataStore.EmailKey
import com.example.budgetlyapp.common.dataStore.IncomeValueKey
import com.example.budgetlyapp.common.dataStore.UserLastNameKey
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

            dataStoreRepository.setString(EmailKey.key, userInfo.email)
            dataStoreRepository.setString(UserNameKey.key, userInfo.userName)
            dataStoreRepository.setString(UserLastNameKey.key, userInfo.userLastName)
            dataStoreRepository.setDouble(IncomeValueKey.key, userInfo.incomeValue)
        } catch (e: Exception) {
            Log.e("FetchUserInfo", "Error fetching user info", e)
        }
    }
}