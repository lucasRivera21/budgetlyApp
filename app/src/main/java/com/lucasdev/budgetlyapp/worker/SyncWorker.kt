package com.lucasdev.budgetlyapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lucasdev.budgetlyapp.common.domain.useCases.DownloadExpensesUseCase
import com.lucasdev.budgetlyapp.common.domain.useCases.SyncExpensesUseCase
import com.lucasdev.budgetlyapp.common.domain.useCases.SyncTasksUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

private const val TAG = "SyncWorker"

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncExpensesUseCase: SyncExpensesUseCase,
    private val syncTasksUseCase: SyncTasksUseCase,
    private val downloadExpenses: DownloadExpensesUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

        return try {
            //UpdateTables
            syncExpensesUseCase()
            syncTasksUseCase()

            //DownloadTables
            downloadExpenses()

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing data: ${e.message}")
            Result.failure()
        }

    }
}