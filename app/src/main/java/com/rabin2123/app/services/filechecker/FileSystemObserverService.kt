package com.rabin2123.app.services.filechecker

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ServiceCompat
import com.rabin2123.app.services.filechecker.utils.NotificationHelper
import org.koin.core.component.get
import org.koin.core.component.KoinComponent

/**
 * Foreground service with file Observer
 *
 */
class FileSystemObserverService : Service(), KoinComponent {

    private val notificationHelper: NotificationHelper = get()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return START_STICKY
    }

    /**
     * start foreground service and create file observer instance
     *
     */
    private fun start() {
        startAsForegroundService()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startFileObserver()
        }, 30000)
    }

    /**
     * start foreground service
     *
     */
    private fun startAsForegroundService() {
        notificationHelper.createNotificationChannel()
        Log.d("TAG!", "StartForegroundService")
        ServiceCompat.startForeground(
            this@FileSystemObserverService,
            1,
            notificationHelper.buildNotification(),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            }
        )
    }

    /**
     * start file observer
     *
     */
    private fun startFileObserver() {
        val fileObserver = FileSystemObserver(FileSystemObserver.DOWNLOADS_PATH)
        fileObserver.startWatching()

    }

    override fun onDestroy() {
        Log.d("TAG!", "onDestroyService")
//        sendBroadcast(
//            Intent(Actions.START.toString()).setClassName(
//                this@FileSystemObserverService.packageName,
//                "com.rabin2123.app.services.filechecker.StartupReceiverFileSystem"
//            )
//        )
        super.onDestroy()
    }

    enum class Actions {
        START, STOP
    }
}