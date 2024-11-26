package com.rabin2123.data.local.sharedprefs.models

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