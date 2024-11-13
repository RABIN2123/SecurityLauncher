package com.rabin2123.app.services.filechecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartupReceiverFileSystem: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG!", "BroadcastReceiverStart")
        context?.startForegroundService(Intent(context.applicationContext, FileSystemObserverService::class.java))
    }
}