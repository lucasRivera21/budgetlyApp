package com.example.budgetlyapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.budgetlyapp.alarm.AlarmScheduler
import com.example.budgetlyapp.common.dataStore.DataStoreRepository
import com.example.budgetlyapp.common.dataStore.LastExecuteTaskWorkerKey
import com.example.budgetlyapp.common.utils.AMOUNT_TASK_TO_SHOW
import com.example.budgetlyapp.common.utils.getDrawableIdByName
import com.example.budgetlyapp.common.utils.scheduleNewNotification
import com.example.budgetlyapp.features.expense.data.repository.CreateExpenseRepository
import com.example.budgetlyapp.features.expense.data.repository.ExpenseRepository
import com.example.budgetlyapp.features.expense.domain.models.TaskUpload
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "TaskWorker"

@HiltWorker
class TaskWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataStoreRepository: DataStoreRepository,
    private val expenseRepository: ExpenseRepository,
    private val createExpenseRepository: CreateExpenseRepository,
    private val alarmScheduler: AlarmScheduler
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val lastDateExecute = dataStoreRepository.getString(LastExecuteTaskWorkerKey.key)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentMonthAndYear = LocalDate.now().withDayOfMonth(1).format(formatter)

        if (lastDateExecute.isEmpty()) {
            dataStoreRepository.setString(LastExecuteTaskWorkerKey.key, currentMonthAndYear)
            return Result.success()
        }

        if (currentMonthAndYear == lastDateExecute) {
            return Result.success()
        }

        try {
            val taskUploadList = generateTaskUploadList(currentMonthAndYear, formatter)
            uploadTask(taskUploadList)
            dataStoreRepository.setString(LastExecuteTaskWorkerKey.key, currentMonthAndYear)
        } catch (e: Exception) {
            Log.e(TAG, "doWork: ${e.message}", e)
            return Result.failure()
        }

        return Result.success()
    }

    private suspend fun generateTaskUploadList(
        currentDate: String,
        formatter: DateTimeFormatter
    ): List<TaskUpload> {
        val extraMonth = AMOUNT_TASK_TO_SHOW - 1

        val currentDateLocalDate = LocalDate.parse(currentDate, formatter)
        val currentDatePlusExtraMonth =
            currentDateLocalDate.plusMonths(extraMonth.toLong()).format(formatter)

        val taskWithMostCurrentDate = expenseRepository.getTaskWithMostCurrentDate()
        val taskUploadList = mutableListOf<TaskUpload>()

        taskWithMostCurrentDate.forEach { taskResponse ->
            if (currentDatePlusExtraMonth > taskResponse.dateDue) {
                val dateDueLocalDate =
                    LocalDate.parse(taskResponse.dateDue, formatter)
                val newDateDue = dateDueLocalDate.plusMonths(1).format(formatter)
                taskUploadList.add(
                    TaskUpload(
                        taskName = taskResponse.taskName,
                        expenseId = taskResponse.expenseId,
                        expenseGroupId = taskResponse.expenseGroupId,
                        requestCode = null,
                        dateDue = newDateDue,
                        hasDayDue = taskResponse.hasDayDue,
                        tag = taskResponse.tag,
                        amount = taskResponse.amount,
                        hasNotification = taskResponse.hasNotification
                    )
                )
            }
        }

        return taskUploadList
    }

    private suspend fun uploadTask(taskUploadList: List<TaskUpload>) {
        val taskToUpload = taskUploadList.map { taskUpload ->
            var requestCode: Int? = null
            if (taskUpload.hasNotification) {
                requestCode = scheduleNewNotification(
                    taskUpload.dateDue!!,
                    getDrawableIdByName(applicationContext, taskUpload.tag.iconId),
                    taskUpload.taskName,
                    taskUpload.amount,
                    applicationContext,
                    alarmScheduler
                )
            }
            taskUpload.copy(requestCode = requestCode)
        }

        createExpenseRepository.createTask(taskToUpload)
    }
}