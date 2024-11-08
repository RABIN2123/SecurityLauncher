package com.rabin2123.app.services.filechecker

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Environment
import android.os.FileObserver
import android.os.FileObserver.CREATE
import android.os.FileObserver.DELETE
import android.os.FileObserver.MODIFY
import android.os.FileObserver.MOVED_FROM
import android.os.FileObserver.MOVED_TO
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.rabin2123.app.R
import com.rabin2123.app.services.filechecker.utils.NotificationHelper

class FileSystemObserverService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startAsForegroundService()
        return super.onStartCommand(intent, flags, startId)
    }


    private fun startAsForegroundService() {
        NotificationHelper.createNotificationChannel(this@FileSystemObserverService)
        Log.d("TAG!", "StartForegroundService")
        ServiceCompat.startForeground(
            this@FileSystemObserverService,
            1,
            NotificationHelper.buildNotification(this@FileSystemObserverService),
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            }
        )
    }
}