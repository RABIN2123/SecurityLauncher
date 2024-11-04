package com.rabin2123.domain

import com.rabin2123.data.local.applist.AppListData
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.models.toDomain

class RepositoryImpl(private val localData: AppListData): Repository {
    override suspend fun getAllAppList(): List<AppObject> {
        return localData.getAppList().toDomain()
    }
}