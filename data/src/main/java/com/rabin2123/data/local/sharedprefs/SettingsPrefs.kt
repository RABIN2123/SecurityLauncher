package com.rabin2123.data.local.sharedprefs

import com.rabin2123.data.local.sharedprefs.models.SettingsData

interface SettingsPrefs {
    suspend fun updateSettingsList(settingsList: SettingsData)
    suspend fun getSettingsList(): SettingsData
    suspend fun getAdminPassword(): String
    suspend fun setAdminPassword(password: String)
}