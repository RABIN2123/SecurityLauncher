package com.rabin2123.domain.repositoryinterfaces

import com.rabin2123.domain.models.AppObject
import kotlinx.coroutines.flow.Flow

interface LocalRepositoryForUser {
    fun getAllowedAppList(): Flow<List<AppObject>>
}