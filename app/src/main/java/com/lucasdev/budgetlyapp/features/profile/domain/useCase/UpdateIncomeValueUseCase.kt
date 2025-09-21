package com.lucasdev.budgetlyapp.features.profile.domain.useCase

import com.lucasdev.budgetlyapp.common.dataStore.DataStoreRepository
import com.lucasdev.budgetlyapp.common.dataStore.IncomeValueKey
import com.lucasdev.budgetlyapp.features.profile.data.EditPersonalDataTask
import javax.inject.Inject

class UpdateIncomeValueUseCase @Inject constructor(
    private val editPersonalDataTask: EditPersonalDataTask,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(incomeValue: String): Result<String> {
        val updateIncomeResult = editPersonalDataTask.updateIncomeValue(incomeValue)
        if (updateIncomeResult.isSuccess) {
            dataStoreRepository.setDouble(IncomeValueKey.key, incomeValue.toDouble())
        }
        return updateIncomeResult
    }
}