package com.example.budgetlyapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.budgetlyapp.common.utils.CHANNEL_DESCRIPTION
import com.example.budgetlyapp.common.utils.CHANNEL_ID
import com.example.budgetlyapp.common.utils.CHANNEL_NAME
import com.example.budgetlyapp.worker.TaskWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BudgetlyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        setUpTaskWorker()
    }

    private fun createNotificationChannel() {
        val name = CHANNEL_NAME
        val descriptionText = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    private fun setUpTaskWorker() {
        val taskWorker = PeriodicWorkRequestBuilder<TaskWorker>(1, TimeUnit.DAYS).build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("taskWorker", ExistingPeriodicWorkPolicy.KEEP, taskWorker)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}