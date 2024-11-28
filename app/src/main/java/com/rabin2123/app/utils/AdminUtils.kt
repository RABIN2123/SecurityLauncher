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
import com.rabin2123.domain.models.SettingsObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//adb shell dpm set-device-owner com.rabin2123.securitylauncher/com.rabin2123.app.services.adminreceiver.AdminReceiver
private const val TAG = "AdminUtils"

sealed interface SettingsEvent {
    class BlockApps(val appList: Array<String>, val state: Boolean) : SettingsEvent
    class BlockGps(val state: Boolean) : SettingsEvent
    class BlockUsb(val state: Boolean) : SettingsEvent
    class BlockCamera(val state: Boolean) : SettingsEvent
}

class AdminUtils(private val context: Context) {
    private val devicePolicyManager: DevicePolicyManager by lazy {
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }
    private val myDeviceAdmin: ComponentName by lazy {
        ComponentName(context, AdminReceiver::class.java)
    }
    private val _settingsList = MutableStateFlow<SettingsObject?>(null)

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

    fun onEvent(event: SettingsEvent): Boolean {
        if (!devicePolicyManager.isDeviceOwnerApp(context.packageName)) return false
        return when (event) {
            is SettingsEvent.BlockApps -> blockApps(event.appList, event.state)
            is SettingsEvent.BlockGps -> blockGps(event.state)
            is SettingsEvent.BlockUsb -> blockUsb(event.state)
            is SettingsEvent.BlockCamera -> blockCamera(event.state)
        }
    }

    private fun blockApps(appList: Array<String>, state: Boolean): Boolean {
        return devicePolicyManager.setPackagesSuspended(
            myDeviceAdmin,
            appList,
            state
        ).isEmpty()
    }

    private fun blockGps(state: Boolean): Boolean {
        try {
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
            return true
        } catch (ex: Exception) {
            Log.d(TAG, "BlockGPS($state): $ex")
            return false
        }
    }

    private fun blockUsb(state: Boolean): Boolean {
        try {
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
            return true
        } catch (ex: Exception) {
            Log.d(TAG, "BlockUsb($state): $ex")
            return false
        }
    }

    private fun blockCamera(state: Boolean): Boolean {
        try {
            devicePolicyManager.setCameraDisabled(myDeviceAdmin, state)
            return true
        } catch (ex: Exception) {
            Log.d(TAG, "BlockCamera($state): $ex")
            return false
        }
    }
}