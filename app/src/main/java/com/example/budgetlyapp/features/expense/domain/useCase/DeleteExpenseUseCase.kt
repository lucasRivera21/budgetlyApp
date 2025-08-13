package com.example.budgetlyapp.features.expense.domain.useCase

import com.example.budgetlyapp.alarm.AlarmScheduler
import com.example.budgetlyapp.features.expense.data.repository.ExpenseRepository
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(expenseId: String) {
        val taskToUploadNotificationResponse = expenseRepository.getTaskList(expenseId)
        taskToUploadNotificationResponse.forEach {
            if (it.requestCode != null) {
                alarmScheduler.cancel(it.requestCode)
            }
        }

        expenseRepository.deleteExpense(expenseId)
    }
}