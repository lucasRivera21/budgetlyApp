package com.example.budgetlyapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat.canScheduleExactAlarms
import com.example.budgetlyapp.common.domain.models.AlarmItem
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.ZoneId
import javax.inject.Inject

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}

class AlarmSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmItem: AlarmItem) {
        if (!canScheduleExactAlarms(alarmManager)) return
        val dateInMillis =
            alarmItem.alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("NOTIFICATION_TITLE", alarmItem.title)
            putExtra("NOTIFICATION_MESSAGE", alarmItem.message)
            putExtra("NOTIFICATION_REQUEST_CODE", alarmItem.requestCode)
        }.let { intent ->
            PendingIntent.getBroadcast(
                context,
                alarmItem.requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            dateInMillis,
            alarmIntent
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        TODO("Not yet implemented")
    }
}