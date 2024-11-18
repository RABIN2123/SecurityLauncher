package com.rabin2123.domain.repositoryinterfaces

import com.rabin2123.domain.models.AppObject

interface LocalRepository {
    suspend fun getAllAppList(): List<AppObject>
}