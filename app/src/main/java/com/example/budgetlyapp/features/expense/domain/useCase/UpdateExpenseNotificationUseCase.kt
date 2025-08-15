package com.example.budgetlyapp.features.expense.domain.useCase

import android.content.Context
import com.example.budgetlyapp.alarm.AlarmScheduler
import com.example.budgetlyapp.common.utils.getDrawableIdByName
import com.example.budgetlyapp.common.utils.scheduleNewNotification
import com.example.budgetlyapp.features.expense.data.repository.ExpenseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateExpenseNotificationUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val expenseRepository: ExpenseRepository,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(
        expenseId: String,
        hasNotification: Boolean
    ) {
        val taskToUploadNotificationResponse = expenseRepository.getTaskList(expenseId)
        taskToUploadNotificationResponse.forEach {
            val requestCode =
                if (!hasNotification) {
                    alarmScheduler.cancel(it.requestCode!!)
                    null
                } else {
                    //This function return a new requestCode
                    scheduleNewNotification(
                        it.dateDue,
                        getDrawableIdByName(context, it.iconId),
                        it.taskName,
                        it.amount,
                        context,
                        alarmScheduler
                    )
                }
            expenseRepository.updateRequestCode(expenseId, requestCode, it.dateDue)
        }

        expenseRepository.updateExpenseNotification(expenseId, hasNotification)
    }
}