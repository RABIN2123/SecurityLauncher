package com.rabin2123.domain.repositoryimples

import com.rabin2123.data.local.LocalDataForAdmin
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelper
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.models.SettingsObject
import com.rabin2123.domain.models.toData
import com.rabin2123.domain.models.toDomain
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForAdmin
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForUser
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(private val localDataForAdmin: LocalDataForAdmin, private val localDataForUser : AllowedAppListHelper):
    LocalRepositoryForAdmin, LocalRepositoryForUser {
    override suspend fun getAllAppList(): List<AppObject> {
        return localDataForAdmin.getAppList().toDomain()
    }

    override suspend fun setAllowedAppList(allowedAppList: List<AppObject>) {
        localDataForUser.insertAllowedApps(allowedAppList.toData())
    }

    override suspend fun deleteAllowedAppList(allowedAppList: List<AppObject>) {
        localDataForUser.deleteAllowedApps(allowedAppList.toData())
    }

    override suspend fun deleteAllAllowedAppList() {
        localDataForUser.deleteAllAllowedApps()
    }

    override fun getAllowedAppList(): Flow<List<AppObject>> {
        return localDataForUser.getAllowedApps().toDomain()
    }

    override suspend fun getSettingsList(): SettingsObject {
        return localDataForAdmin.getSettingsList().toDomain()
    }

    override suspend fun updateSettingsList(settingsList: SettingsObject) {
        localDataForAdmin.updateSettingsList(settingsList.toData())
    }

    override suspend fun setAdminPassword(password: String) {
        localDataForAdmin.setAdminPassword(password)
    }

    override suspend fun getAdminPassword(): String {
        return localDataForAdmin.getAdminPassword()
    }
}