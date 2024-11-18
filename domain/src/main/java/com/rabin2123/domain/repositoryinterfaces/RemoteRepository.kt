package com.rabin2123.domain.repositoryinterfaces

interface RemoteRepository {
    suspend fun getInfoAboutHashFile(hashSample: String): Boolean
}