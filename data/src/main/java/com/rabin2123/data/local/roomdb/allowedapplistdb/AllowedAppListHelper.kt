package com.rabin2123.data.local.roomdb.allowedapplistdb

import kotlinx.coroutines.flow.Flow

interface AllowedAppListHelper {
    /**
     * insert list with allowed app list
     *
     * @param allowedAppList list with allowed app for user
     */
    suspend fun insertAllowedApps(allowedAppList: List<AllowedAppEntity>)

    /**
     * delete selected apps from allowed app list
     *
     * @param allowedAppList list with allowed apps for user that need to be delete
     */
    suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppEntity>)

    /**
     * delete all apps from allowed app list except launcher settings
     *
     */
    suspend fun deleteAllAllowedApps()

    /**
     * get allowed app list ordered by app name
     *
     * @return list with allowed app for user
     */
    fun getAllowedApps(): Flow<List<AllowedAppEntity>>
}