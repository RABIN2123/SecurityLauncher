package com.rabin2123.data.local

import com.rabin2123.data.local.globalapplist.GlobalAppListData
import com.rabin2123.data.local.globalapplist.models.AppObjectData
import com.rabin2123.data.local.sharedprefs.settingsprefs.models.SettingsData
import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefs

class LocalDataForAdminImpl(
    private val globalAppList: GlobalAppListData,
    private val settingsDb: SettingsPrefs
) : LocalDataForAdmin {
    override suspend fun getAppList(): List<AppObjectData> {
        return globalAppList.getAppList()
    }

    override suspend fun updateSettingsList(settingsList: SettingsData) {
        settingsDb.updateSettingsList(settingsList)
    }

    override suspend fun getSettingsList(): SettingsData {
        return settingsDb.getSettingsList()
    }

    override suspend fun getAdminPassword(): ByteArray {
        return settingsDb.getAdminPassword()
    }

    override suspend fun setAdminPassword(password: ByteArray) {
        settingsDb.setAdminPassword(password)
    }

    override suspend fun encryptPassword(password: String): ByteArray {
        return settingsDb.encryptPassword(password)
    }
}