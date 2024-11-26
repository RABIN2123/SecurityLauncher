package com.rabin2123.data.local.roomdb.allowedapplistdb

import kotlinx.coroutines.flow.Flow

class AllowedAppListHelperImpl(private val dao: AllowedAppListDao): AllowedAppListHelper {
    override suspend fun insertAllowedApps(allowedAppList: List<AllowedAppEntity>) {
        dao.insertAllowedApps(allowedAppList)
    }

    override suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppEntity>) {
        dao.deleteAllowedApps(allowedAppList)
    }

    override suspend fun deleteAllAllowedApps() {
        dao.deleteAllAllowedApps()
    }

    override fun getAllowedApps(): Flow<List<AllowedAppEntity>> {
        return dao.getAllowedApps()
    }
}