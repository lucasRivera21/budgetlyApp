package com.lucasdev.budgetlyapp.features.home.domain.useCase

import com.lucasdev.budgetlyapp.common.domain.useCases.DownloadExpensesUseCase
import com.lucasdev.budgetlyapp.features.home.data.HomeTask
import javax.inject.Inject

class DownloadDataUseCase @Inject constructor(
    private val repository: HomeTask,
    private val downloadExpensesUseCase: DownloadExpensesUseCase
) {
    suspend operator fun invoke() {
        val expenseCount = repository.countExpenses()

        if (expenseCount != 0) {
            return
        }

        downloadExpensesUseCase()
    }
}