package com.rabin2123.data.local.sharedprefs.settingsprefs.models

/**
 * data class for launcher settings
 *
 * @property sendToMlBazaar turn on/off send sample to malwareBazaar
 * @property blockSettings block/unblock ability to open settings
 * @property blockGps block/unblock ability to use gps
 * @property blockUsb block/unblock ability to use usb-connection for data exchange
 * @property blockCamera block/unblock ability to use camera
 */
data class SettingsData(
    val sendToMlBazaar: Boolean,
    val blockSettings: Boolean,
    val blockGps: Boolean,
    val blockUsb: Boolean,
    val blockCamera: Boolean
) {
    companion object {
        const val PREFS_NAME = "launcher_settings_prefs"
        const val ADMIN_PASSWORD = "admin_password"
        const val ML_BAZAAR = "send_to_ml_bazaar"
        const val BLOCK_SETTINGS = "block_settings"
        const val BLOCK_GPS = "block_gps"
        const val BLOCK_USB = "block_usb"
        const val BLOCK_CAMERA = "block_camera"
    }
}