package com.rabin2123.app.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.UserManager
import android.provider.Settings
import com.rabin2123.app.MainActivity
import com.rabin2123.app.services.adminreceiver.AdminReceiver

class AdminUtils(private val context: Context) {
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
    }

    fun setAppHowLauncher() {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
            val filter = IntentFilter(Intent.ACTION_MAIN)
            filter.addCategory(Intent.CATEGORY_HOME)
            filter.addCategory(Intent.CATEGORY_DEFAULT)
            val activity = ComponentName(context, MainActivity::class.java)
            devicePolicyManager.addPersistentPreferredActivity(myDeviceAdmin, filter, activity)
        }
    }

    fun unsetAppHowLauncher() {

    }


    fun getStateAdminActive(): Boolean {
        return devicePolicyManager.isAdminActive(myDeviceAdmin)
    }

    fun blockApps(appList: Array<String>, state: Boolean) {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName))
            devicePolicyManager.setPackagesSuspended(myDeviceAdmin, appList, state)
    }

    fun blockGps(state: Boolean) {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
            devicePolicyManager.setSecureSetting(
                myDeviceAdmin,
                Settings.Secure.LOCATION_MODE,
                if (state) Settings.Secure.LOCATION_MODE_OFF.toString() else null
            )
        }
    }

    fun blockUsb(state: Boolean) {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName))
            if (state) {
                devicePolicyManager.addUserRestriction(
                    myDeviceAdmin,
                    UserManager.DISALLOW_USB_FILE_TRANSFER
                )
            } else {
                devicePolicyManager.clearUserRestriction(
                    myDeviceAdmin,
                    UserManager.DISALLOW_USB_FILE_TRANSFER
                )
            }
    }

    fun blockCamera(state: Boolean) {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName))
            devicePolicyManager.setCameraDisabled(myDeviceAdmin, state)
    }

}