package com.rabin2123.domain.models

import com.rabin2123.data.local.sharedprefs.models.SettingsData


data class SettingsObject(
    val sendToMlBazaar: Boolean,
    val blockSettings: Boolean,
    val blockGps: Boolean,
    val blockUsb: Boolean,
    val blockCamera: Boolean
)

fun SettingsData.toDomain(): SettingsObject {
    return SettingsObject(
        sendToMlBazaar = this.sendToMlBazaar,
        blockSettings = this.blockSettings,
        blockGps = this.blockGps,
        blockUsb = this.blockUsb,
        blockCamera = this.blockCamera
    )
}

fun SettingsObject.toData(): SettingsData {
    return SettingsData(
        sendToMlBazaar = this.sendToMlBazaar,
        blockSettings = this.blockSettings,
        blockGps = this.blockGps,
        blockUsb = this.blockUsb,
        blockCamera = this.blockCamera
    )
}

