package com.rabin2123.app.services.adminreceiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        Log.d("TAG!", "admin enabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        Log.d("TAG!", "admin disabled")
    }
}