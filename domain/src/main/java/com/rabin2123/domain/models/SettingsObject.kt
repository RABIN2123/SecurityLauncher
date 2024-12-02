package com.rabin2123.domain.models

import com.rabin2123.data.local.sharedprefs.settingsprefs.models.SettingsData

/**
 * data class with settings for launcher
 *
 * @property sendToMlBazaar turn on/off send sample to malwareBazaar
 * @property blockSettings block/unblock ability to open settings
 * @property blockGps block/unblock ability to use gps
 * @property blockUsb block/unblock ability to use usb-connection for data exchange
 * @property blockCamera block/unblock ability to use camera
 */
data class SettingsObject(
    val sendToMlBazaar: Boolean,
    val blockSettings: Boolean,
    val blockGps: Boolean,
    val blockUsb: Boolean,
    val blockCamera: Boolean
)

/**
 * conversion SettingsData to SettingsObject
 *
 * @return same data but with SettingsObject of type
 */
fun SettingsData.toDomain(): SettingsObject {
    return SettingsObject(
        sendToMlBazaar = this.sendToMlBazaar,
        blockSettings = this.blockSettings,
        blockGps = this.blockGps,
        blockUsb = this.blockUsb,
        blockCamera = this.blockCamera
    )
}

/**
 * conversion SettingsObject to SettingsData
 *
 * @return same data but with SettingsData of type
 */
fun SettingsObject.toData(): SettingsData {
    return SettingsData(
        sendToMlBazaar = this.sendToMlBazaar,
        blockSettings = this.blockSettings,
        blockGps = this.blockGps,
        blockUsb = this.blockUsb,
        blockCamera = this.blockCamera
    )
}

