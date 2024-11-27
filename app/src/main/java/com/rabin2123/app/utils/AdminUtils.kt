package com.rabin2123.app.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.UserManager
import android.provider.Settings
import android.util.Log
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
            devicePolicyManager.addUserRestriction(
                myDeviceAdmin,
                UserManager.DISALLOW_USER_SWITCH
            )
        }
    }

    fun unsetAppHowLauncher() {
        Log.d("TAG!", "unsetAppHowLauncher")

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                devicePolicyManager.setLocationEnabled(myDeviceAdmin, !state)
            } else {
                devicePolicyManager.setSecureSetting(
                    myDeviceAdmin,
                    Settings.Secure.LOCATION_MODE,
                    if (state) Settings.Secure.LOCATION_MODE_OFF.toString() else null
                )
            }
            if (state) {
                devicePolicyManager.addUserRestriction(
                    myDeviceAdmin,
                    UserManager.DISALLOW_SHARE_LOCATION
                )
            } else {
                devicePolicyManager.clearUserRestriction(
                    myDeviceAdmin,
                    UserManager.DISALLOW_SHARE_LOCATION
                )
            }
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