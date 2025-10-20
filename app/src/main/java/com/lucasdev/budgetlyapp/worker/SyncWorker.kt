package com.lucasdev.budgetlyapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lucasdev.budgetlyapp.common.domain.useCases.SyncExpensesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncExpensesUseCase: SyncExpensesUseCase
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

        syncExpensesUseCase()

        return Result.success()
    }
}