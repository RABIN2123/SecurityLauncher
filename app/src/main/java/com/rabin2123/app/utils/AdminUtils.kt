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

//adb shell dpm set-device-owner com.rabin2123.securitylauncher/com.rabin2123.app.services.adminreceiver.AdminReceiver
private const val TAG = "AdminUtils"

sealed interface SettingsEvent {
    /**
     * block/unblock any apps
     * create for block device settings app for user
     *
     * @property appList list with apps for block
     * @property state block/unblock apps
     */
    class BlockApps(val appList: Array<String>, val state: Boolean) : SettingsEvent

    /**
     * block/unblock gps
     *
     * @property state block/unblock
     */
    class BlockGps(val state: Boolean) : SettingsEvent

    /**
     * block/unblock usb connection
     *
     * @property state block/unblock
     */
    class BlockUsb(val state: Boolean) : SettingsEvent

    /**
     * block/unblock camera
     *
     * @property state block/unblock
     */
    class BlockCamera(val state: Boolean) : SettingsEvent
}

/**
 * control admin/device-owner features
 *
 * @property context context
 */
class AdminUtils(private val context: Context) {
    private val devicePolicyManager: DevicePolicyManager by lazy {
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }
    private val myDeviceAdmin: ComponentName by lazy {
        ComponentName(context, AdminReceiver::class.java)
    }

    /**
     * open settings to enable admin mode(not device owner)
     *
     */
    fun setAdminPermission() {
        context.startActivity(
            Intent().setComponent(
                ComponentName(
                    "com.android.settings", "com.android.settings.DeviceAdminSettings"
                )
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    /**
     * set app how launcher on device
     * work ONLY in device-owner mode
     *
     */
    fun setAppHowLauncher() {
        if (devicePolicyManager.isDeviceOwnerApp(context.packageName)) {
            val filter = IntentFilter(Intent.ACTION_MAIN)
            filter.addCategory(Intent.CATEGORY_HOME)
            filter.addCategory(Intent.CATEGORY_DEFAULT)
            val activity = ComponentName(context, MainActivity::class.java)
            devicePolicyManager.addPersistentPreferredActivity(myDeviceAdmin, filter, activity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                devicePolicyManager.addUserRestriction(
                    myDeviceAdmin,
                    UserManager.DISALLOW_USER_SWITCH
                )
        }
    }

    /**
     * unset app how launcher on device
     *NOT WORK
     */
    fun unsetAppHowLauncher() {
        Log.d("TAG!", "unsetAppHowLauncher")
    }

    /**
     * get admin state(active/not active)
     *
     * @return state
     */
    fun getStateAdminActive(): Boolean {
        return devicePolicyManager.isAdminActive(myDeviceAdmin)
    }

    /**
     * set quality password for user on device
     *
     */
    fun setPasswordQuality() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            devicePolicyManager.requiredPasswordComplexity =
                DevicePolicyManager.PASSWORD_COMPLEXITY_MEDIUM
        } else {
            devicePolicyManager.setPasswordQuality(
                myDeviceAdmin,
                DevicePolicyManager.PASSWORD_COMPLEXITY_MEDIUM
            )
        }
    }


//    fun presenceOfPassword(): Boolean {
//        var result: Boolean? = null
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            devicePolicyManager.uses
//            result = devicePolicyManager.isActivePasswordSufficient
//        }
//        Log.d(TAG, "PresenceOfPassword: $result")
//        return result ?: false
//    }

    /**
     * event for work with device-owner settings
     *
     * @param event container with action
     * @return true if the action is successful
     */
    fun onEvent(event: SettingsEvent): Boolean {
        if (!devicePolicyManager.isDeviceOwnerApp(context.packageName)) return false
        return when (event) {
            is SettingsEvent.BlockApps -> blockApps(event.appList, event.state)
            is SettingsEvent.BlockGps -> blockGps(event.state)
            is SettingsEvent.BlockUsb -> blockUsb(event.state)
            is SettingsEvent.BlockCamera -> blockCamera(event.state)
        }
    }

    /**
     * block/unblock any apps
     * create for block device settings app for user
     *
     * @param appList list with apps for block
     * @param state block/unblock apps
     */
    private fun blockApps(appList: Array<String>, state: Boolean): Boolean {
        return devicePolicyManager.setPackagesSuspended(
            myDeviceAdmin,
            appList,
            state
        ).isEmpty()
    }

    /**
     * block/unblock usb connection
     *
     * @param state block/unblock
     */
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

    /**
     * block/unblock usb connection
     *
     * @param state block/unblock
     */
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

    /**
     * block/unblock camera
     *
     * @param state block/unblock
     */
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