package com.rabin2123.app.services.filechecker.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import com.rabin2123.app.R

class NotificationHelper(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotificationChannel() {

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "file_checker_channel",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun buildNotification(contentText: String = "Work"): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("File Protector")
            .setContentText(contentText)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }

    fun updateNotification() {
       notificationManager.notify(1, buildNotification("Bad file detected"))
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "test_channel"
    }
}