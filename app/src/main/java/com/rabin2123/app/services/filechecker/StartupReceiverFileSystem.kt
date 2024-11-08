package com.rabin2123.app.services.filechecker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class StartupReceiverFileSystem: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG!", "BroadcastReceiverStart")
        context?.startForegroundService(Intent(context, FileSystemObserverService::class.java))
    }
}