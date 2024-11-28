package com.rabin2123.data.local.sharedprefs.settingsprefs

import com.rabin2123.data.local.sharedprefs.models.SettingsData

interface SettingsPrefs {
    suspend fun updateSettingsList(settingsList: SettingsData)
    suspend fun getSettingsList(): SettingsData
    suspend fun getAdminPassword(): ByteArray
    suspend fun setAdminPassword(password: ByteArray)
    suspend fun getAesKeyAlias(): String
    suspend fun encryptPassword(password: String): ByteArray
    suspend fun decryptPassword(password: ByteArray): String
    suspend fun getIv(): String
    suspend fun setIv(iv: String)
}