package com.rabin2123.data.local.roomdb

import kotlinx.coroutines.flow.Flow

class AllowedAppListHelperImpl(private val dao: AllowedAppListDao): AllowedAppListHelper {
    override suspend fun insertAllowedApps(allowedAppList: List<AllowedAppListEntity>) {
        dao.insertAllowedApps(allowedAppList)
    }

    override suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppListEntity>) {
        dao.deleteAllowedApps(allowedAppList)
    }

    override fun getAllowedApps(): Flow<List<AllowedAppListEntity>> {
        return dao.getAllowedApps()
    }
}