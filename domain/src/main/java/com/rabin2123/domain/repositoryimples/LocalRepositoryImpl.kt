package com.rabin2123.domain.repositoryimples

import com.rabin2123.data.local.globalapplist.GlobalAppListData
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.models.toDomain
import com.rabin2123.domain.repositoryinterfaces.LocalRepository

class LocalRepositoryImpl(private val localData: GlobalAppListData):
    LocalRepository {
    override suspend fun getAllAppList(): List<AppObject> {
        return localData.getAppList().toDomain()
    }
}