package com.rabin2123.app.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.rabin2123.app.MainActivity
import com.rabin2123.app.services.adminreceiver.AdminReceiver

class KioskUtil(private val context: Context) {
    private val devicePolicyManager: DevicePolicyManager by lazy {
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }
    private val myDeviceAdmin: ComponentName by lazy {
        ComponentName(context, AdminReceiver::class.java)
    }
    fun setAdminPermission() {
        context.startActivity(
            Intent().setComponent(
                ComponentName(
                    "com.android.settings", "com.android.settings.DeviceAdminSettings"
                )
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName)){
            val filter = IntentFilter(Intent.ACTION_MAIN)
            filter.addCategory(Intent.CATEGORY_HOME)
            filter.addCategory(Intent.CATEGORY_DEFAULT)
            val activity = ComponentName(context, MainActivity::class.java)
            devicePolicyManager.addPersistentPreferredActivity(myDeviceAdmin, filter, activity)

        }
    }


    fun getStateAdminActive(): Boolean {
        return devicePolicyManager.isAdminActive(myDeviceAdmin)
    }

    fun blockApps(appList: Array<String>, suspended: Boolean) {
        if(devicePolicyManager.isDeviceOwnerApp(context.packageName))
            devicePolicyManager.setPackagesSuspended(myDeviceAdmin, appList, suspended)
    }
}