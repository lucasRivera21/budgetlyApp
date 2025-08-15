package com.example.budgetlyapp.features.expense.domain.useCase

import android.content.Context
import android.util.Log
import com.example.budgetlyapp.alarm.AlarmScheduler
import com.example.budgetlyapp.common.domain.models.ExpenseModel
import com.example.budgetlyapp.common.utils.AMOUNT_TASK_TO_SHOW
import com.example.budgetlyapp.common.utils.convertDayMonthYearToDate
import com.example.budgetlyapp.common.utils.getDrawableIdByName
import com.example.budgetlyapp.common.utils.getTodayDate
import com.example.budgetlyapp.common.utils.scheduleNewNotification
import com.example.budgetlyapp.features.expense.data.repository.CreateExpenseTask
import com.example.budgetlyapp.features.expense.domain.models.TaskUpload
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val TAG = "SaveExpenseUseCase"

class SaveExpenseUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val createExpenseTask: CreateExpenseTask,
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(expenseModel: ExpenseModel) {
        try {
            val expenseId = createExpenseTask.createExpense(expenseModel)
            val taskList = createTaskList(expenseModel, expenseId)
            createExpenseTask.createTask(taskList)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating expense: ${e.message}")
        }
    }

    private fun createTaskList(expenseModel: ExpenseModel, expenseId: String): List<TaskUpload> {
        val taskList = mutableListOf<TaskUpload>()

        val hasDayDue = expenseModel.day != null
        val dayDue = expenseModel.day ?: 31

        val currentDay = getTodayDate("dd").toInt()
        val currentMonth = getTodayDate("MM").toInt()
        val currentYear = getTodayDate("yyyy").toInt()

        for (i in 1..AMOUNT_TASK_TO_SHOW) {
            val monthSum = if (currentDay > dayDue) currentMonth + i else currentMonth + i - 1
            val newYear = if (monthSum > 12) currentYear + 1 else currentYear
            val newMonth = if (monthSum > 12) monthSum - 12 else monthSum

            val dateDue = convertDayMonthYearToDate(dayDue, newMonth, newYear)

            var requestCode: Int? = null
            if (expenseModel.hasNotification) {
                requestCode =
                    scheduleNewNotification(
                        dateDue,
                        getDrawableIdByName(context, expenseModel.tag.iconId),
                        expenseModel.expenseName,
                        expenseModel.amount,
                        context,
                        alarmScheduler
                    )
            }

            val task = TaskUpload(
                taskName = expenseModel.expenseName,
                expenseId = expenseId,
                expenseGroupId = expenseModel.expenseGroupId,
                requestCode = requestCode,
                dateDue = dateDue,
                hasDayDue = hasDayDue,
                tag = expenseModel.tag,
                amount = expenseModel.amount,
                hasNotification = expenseModel.hasNotification
            )

            taskList.add(task)
        }

        return taskList
    }


}