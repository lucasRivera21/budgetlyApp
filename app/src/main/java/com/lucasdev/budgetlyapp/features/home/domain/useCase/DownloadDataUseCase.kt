package com.lucasdev.budgetlyapp.features.home.domain.useCase

import com.lucasdev.budgetlyapp.features.home.data.HomeTask
import com.lucasdev.budgetlyapp.features.home.data.toExepenseEntity
import javax.inject.Inject

class DownloadDataUseCase @Inject constructor(private val repository: HomeTask) {
    suspend operator fun invoke() {
        val expenseCount = repository.countExpenses()

        if (expenseCount != 0) {
            return
        }

        downloadExpenses()
    }

    private suspend fun downloadExpenses() {
        val expenseApiDTOList = repository.fetchExpenses()

        if (expenseApiDTOList.isEmpty()) {
            return
        }

        repository.deleteExpenses()

        val expenseEntityList = expenseApiDTOList.map { it.toExepenseEntity() }

        repository.insertExpenses(expenseEntityList)
    }
}