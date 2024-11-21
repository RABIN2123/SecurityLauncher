package com.rabin2123.data.local.roomdb

import kotlinx.coroutines.flow.Flow

interface AllowedAppListHelper {
    suspend fun insertAllowedApps(allowedAppList: List<AllowedAppListEntity>)
    suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppListEntity>)
    suspend fun getAllowedApps(): Flow<List<AllowedAppListEntity>>
}