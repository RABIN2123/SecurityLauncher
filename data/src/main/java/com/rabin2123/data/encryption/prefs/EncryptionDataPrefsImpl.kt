package com.rabin2123.data.encryption.prefs

import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefs

class EncryptionDataPrefsImpl(private val settingsDb: SettingsPrefs): EncryptionDataPrefs {
    override suspend fun getAesKeyAlias(): String {
        return settingsDb.getAesKeyAlias()
    }

    override suspend fun getKeyIv(): String {
        return settingsDb.getIv()
    }

    override suspend fun setKeyIv(iv: String) {
        settingsDb.setIv(iv)
    }
}