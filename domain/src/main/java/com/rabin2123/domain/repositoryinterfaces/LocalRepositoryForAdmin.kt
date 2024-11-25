package com.rabin2123.domain.repositoryinterfaces

import com.rabin2123.domain.models.AppObject
import kotlinx.coroutines.flow.Flow

interface LocalRepositoryForAdmin {
    suspend fun getAllAppList(): List<AppObject>
    suspend fun setAllowedAppList(allowedAppList: List<AppObject>)
    suspend fun deleteAllowedAppList(allowedAppList: List<AppObject>)
    suspend fun deleteAllAllowedAppList()
    fun getAllowedAppList(): Flow<List<AppObject>>
}