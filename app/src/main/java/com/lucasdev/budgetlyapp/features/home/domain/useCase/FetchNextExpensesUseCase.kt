package com.lucasdev.budgetlyapp.features.home.domain.useCase

import com.lucasdev.budgetlyapp.common.utils.changeFormatDate
import com.lucasdev.budgetlyapp.features.home.data.HomeTask
import com.lucasdev.budgetlyapp.features.home.data.toNextTaskModel
import com.lucasdev.budgetlyapp.features.home.domain.models.NextTaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchNextExpensesUseCase @Inject constructor(private val homeTask: HomeTask) {
    suspend operator fun invoke(): Flow<Map<String, List<NextTaskModel>>> =
        homeTask.fetchNextExpensesUseCase().map { nextExpenseDTOS ->
            nextExpenseDTOS.map { it.toNextTaskModel() }
                .groupBy { changeFormatDate(it.dateDue, "yyyy-MM-dd", "MMMM") }
        }
}