package com.rabin2123.data.local.roomdb.allowedapplistdb

import kotlinx.coroutines.flow.Flow

interface AllowedAppListHelper {
    suspend fun insertAllowedApps(allowedAppList: List<AllowedAppEntity>)
    suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppEntity>)
    suspend fun deleteAllAllowedApps()
    fun getAllowedApps(): Flow<List<AllowedAppEntity>>
}