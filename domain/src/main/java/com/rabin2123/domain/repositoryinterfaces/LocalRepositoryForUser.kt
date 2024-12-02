package com.rabin2123.domain.repositoryinterfaces

import com.rabin2123.domain.models.AppObject
import kotlinx.coroutines.flow.Flow

interface LocalRepositoryForUser {
    /**
     * get allowed app list ordered by app name
     *
     * @return list with allowed app for user
     */
    fun getAllowedAppList(): Flow<List<AppObject>>

    /**
     * comparison of the entered password with from the database
     *
     * @param password entered password
     * @return result of comparison
     */
    suspend fun checkAdminPassword(password: String): Boolean
}