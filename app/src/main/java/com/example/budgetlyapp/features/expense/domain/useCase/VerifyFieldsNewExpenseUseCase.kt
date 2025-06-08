package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.common.domain.models.TagModel
import javax.inject.Inject

class VerifyFieldsNewExpenseUseCase @Inject constructor() {
    operator fun invoke(
        nameExpense: String,
        amountExpense: String,
        categorySelected: TagModel,
        dayPayString: String,
        hasDayPay: Boolean
    ): Result<String> {
        if (nameExpense.isEmpty()) {
            return Result.failure(Exception("El nombre del gasto no puede estar vacio"))
        }

        if (amountExpense.isEmpty()) {
            return Result.failure(Exception("El monto del gasto no puede estar vacio"))
        }

        if (categorySelected == TagModel()) {
            return Result.failure(Exception("Debes seleccionar una categoria"))
        }

        if (hasDayPay && dayPayString.isEmpty()) {
            return Result.failure(Exception("El dia del gasto no puede estar vacio"))
        }

        return Result.success("")
    }
}