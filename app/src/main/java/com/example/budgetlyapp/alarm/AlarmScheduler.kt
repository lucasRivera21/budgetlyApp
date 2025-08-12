package com.example.budgetlyapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat.canScheduleExactAlarms
import com.example.budgetlyapp.common.domain.models.AlarmItem
import java.util.Calendar

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmItem: AlarmItem) {
        if (!canScheduleExactAlarms(alarmManager)) return
        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                alarmItem.requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + 10000,
            alarmIntent
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        TODO("Not yet implemented")
    }
}