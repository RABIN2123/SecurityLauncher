package com.rabin2123.data.local.sharedprefs.settingsprefs

import com.rabin2123.data.local.sharedprefs.settingsprefs.models.SettingsData

interface SettingsPrefs {
    /**
     * Update settings in shared preferences file
     *
     * @param settingsList list with new settings
     */
    suspend fun updateSettingsList(settingsList: SettingsData)

    /**
     * get settings for launcher
     *
     * @return settings for launcher
     */
    suspend fun getSettingsList(): SettingsData

    /**
     * get admin password for open settings
     *
     * @return encrypted password
     */
    suspend fun getAdminPassword(): ByteArray

    /**
     * set new admin password
     *
     * @param password new admin password
     */
    suspend fun setAdminPassword(password: ByteArray)

    /**
     * encrypt new or input password
     *
     * @param password password
     * @return encrypted password
     */
    suspend fun encryptPassword(password: String): ByteArray

    /**
     * decrypt password
     * NOT USED
     *
     * @param password encrypted password
     * @return decrypted password
     */
    suspend fun decryptPassword(password: ByteArray): String
}