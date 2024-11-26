package com.rabin2123.data.local

import com.rabin2123.data.local.globalapplist.models.AppObjectData
import com.rabin2123.data.local.sharedprefs.models.SettingsData

interface LocalDataForAdmin {
    suspend fun getAppList(): List<AppObjectData>
    suspend fun updateSettingsList(settingsList: SettingsData)
    suspend fun getSettingsList(): SettingsData
    suspend fun getAdminPassword(): String
    suspend fun setAdminPassword(password: String)
}