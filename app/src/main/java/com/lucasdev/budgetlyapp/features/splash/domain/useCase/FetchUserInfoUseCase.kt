package com.lucasdev.budgetlyapp.features.splash.domain.useCase

import android.util.Log
import com.lucasdev.budgetlyapp.common.dataStore.DataStoreRepository
import com.lucasdev.budgetlyapp.common.dataStore.EmailKey
import com.lucasdev.budgetlyapp.common.dataStore.IncomeValueKey
import com.lucasdev.budgetlyapp.common.dataStore.UserLastNameKey
import com.lucasdev.budgetlyapp.common.dataStore.UserNameKey
import com.lucasdev.budgetlyapp.features.splash.data.SplashRepository
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