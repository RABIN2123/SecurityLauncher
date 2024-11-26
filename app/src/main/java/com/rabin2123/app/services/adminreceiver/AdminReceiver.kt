package com.rabin2123.app.services.adminreceiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import com.rabin2123.app.utils.AdminUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AdminReceiver : DeviceAdminReceiver(), KoinComponent {

    private val adminUtil: AdminUtils by inject()

    override fun onEnabled(context: Context, intent: Intent) {
        adminUtil.setAppHowLauncher()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        adminUtil.unsetAppHowLauncher()
    }

}