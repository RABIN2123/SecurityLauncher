package com.rabin2123.data.local

import com.rabin2123.data.local.globalapplist.GlobalAppListData
import com.rabin2123.data.local.globalapplist.models.AppObjectData
import com.rabin2123.data.local.sharedprefs.models.SettingsData
import com.rabin2123.data.local.sharedprefs.SettingsPrefs

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

    override suspend fun getAdminPassword(): String {
        return settingsDb.getAdminPassword()
    }

    override suspend fun setAdminPassword(password: String) {
        settingsDb.setAdminPassword(password)
    }
}