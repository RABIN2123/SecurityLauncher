package com.rabin2123.domain.repositoryinterfaces

import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.models.SettingsObject
import kotlinx.coroutines.flow.Flow

interface LocalRepositoryForAdmin {
    /**
     * Get all apps without systems apps
     *
     * @return list with all apps
     */
    suspend fun getAllAppList(): List<AppObject>

    /**
     * insert list with allowed app list
     *
     * @param allowedAppList list with allowed app for user
     */
    suspend fun setAllowedAppList(allowedAppList: List<AppObject>)

    /**
     * delete selected apps from allowed app list
     *
     * @param allowedAppList list with allowed apps for user that need to be delete
     */
    suspend fun deleteAllowedAppList(allowedAppList: List<AppObject>)

    /**
     * delete all apps from allowed app list except launcher settings
     *
     */
    suspend fun deleteAllAllowedAppList()

    /**
     * get allowed app list ordered by app name
     *
     * @return list with allowed app for user
     */
    fun getAllowedAppList(): Flow<List<AppObject>>

    /**
     * get settings for launcher
     *
     * @return settings for launcher
     */
    suspend fun getSettingsList(): SettingsObject

    /**
     * Update settings in shared preferences file
     *
     * @param settingsList list with new settings
     */
    suspend fun updateSettingsList(settingsList: SettingsObject)

    /**
     * set new admin password
     *
     * @param password new admin password
     */
    suspend fun setAdminPassword(password: String)

    /**
     * comparison of the entered password with from the database
     *
     * @param password entered password
     * @return result of comparison
     */
    suspend fun checkAdminPassword(password: String): Boolean
}