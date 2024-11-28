package com.rabin2123.data.local.sharedprefs.settingsprefs

import android.content.SharedPreferences
import com.rabin2123.data.encryption.helper.EncryptionHelper
import com.rabin2123.data.local.sharedprefs.get
import com.rabin2123.data.local.sharedprefs.models.SettingsData
import com.rabin2123.data.local.sharedprefs.set

class SettingsPrefsImpl(
    private val prefs: SharedPreferences,
    private val encryption: EncryptionHelper
) : SettingsPrefs {
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

    override suspend fun getAdminPassword(): ByteArray {
        return prefs[SettingsData.ADMIN_PASSWORD]
    }

    override suspend fun setAdminPassword(password: ByteArray) {
        prefs[SettingsData.ADMIN_PASSWORD] = password
    }

    override suspend fun getAesKeyAlias(): String {
        return prefs[SettingsPrefsBuilder.AES_KEY_ALIAS]
    }

    override suspend fun encryptPassword(password: String): ByteArray {
        return encryption.encryptionPassword(password)
    }

    override suspend fun decryptPassword(password: ByteArray): String {
        return encryption.decryptionPassword(password)
    }

    override suspend fun getIv(): String {
        return prefs[SettingsPrefsBuilder.IV_KEY]
    }

    override suspend fun setIv(iv: String) {
        prefs[SettingsPrefsBuilder.IV_KEY] = iv
    }
}