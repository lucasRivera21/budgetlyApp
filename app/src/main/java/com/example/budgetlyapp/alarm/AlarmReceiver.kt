package com.example.budgetlyapp.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.budgetlyapp.common.utils.CHANNEL_ID
import com.example.budgetlyapp.common.utils.hasNotificationPermission
import com.example.budgetlyapp.features.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("NOTIFICATION_TITLE") ?: ""
        val message = intent?.getStringExtra("NOTIFICATION_MESSAGE") ?: ""
        val requestCode = intent?.getIntExtra("NOTIFICATION_REQUEST_CODE", 0) ?: 0
        createNotification(context, title, message, requestCode)
    }

    private fun createNotification(
        context: Context,
        title: String,
        message: String,
        requestCode: Int
    ) {
        if (!hasNotificationPermission(context)) return
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination", "notification")
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_agenda)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notification = builder.build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }
}