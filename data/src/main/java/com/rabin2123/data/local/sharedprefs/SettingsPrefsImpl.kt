package com.rabin2123.data.local.sharedprefs

import android.content.SharedPreferences
import com.rabin2123.data.local.sharedprefs.models.SettingsData

class SettingsPrefsImpl(private val prefs: SharedPreferences) : SettingsPrefs {
    override suspend fun updateSettingsList(settingsList: SettingsData) {
        prefs[SettingsData.ML_BAZAAR] = settingsList.sendToMlBazaar
        prefs[SettingsData.BLOCK_SETTINGS] = settingsList.blockSettings
        prefs[SettingsData.BLOCK_GPS] = settingsList.blockGps
        prefs[SettingsData.BLOCK_USB] = settingsList.blockUsb
        prefs[SettingsData.BLOCK_CAMERA] = settingsList.blockCamera
    }

    override suspend fun getSettingsList(): SettingsData {
        return SettingsData(
            sendToMlBazaar = prefs[SettingsData.ML_BAZAAR],
            blockSettings = prefs[SettingsData.BLOCK_SETTINGS],
            blockGps = prefs[SettingsData.BLOCK_GPS],
            blockUsb = prefs[SettingsData.BLOCK_USB],
            blockCamera = prefs[SettingsData.BLOCK_CAMERA]
        )
    }

    override suspend fun getAdminPassword(): String {
        return prefs[SettingsData.ADMIN_PASSWORD]
    }

    override suspend fun setAdminPassword(password: String) {
        prefs[SettingsData.ADMIN_PASSWORD] = password
    }
}