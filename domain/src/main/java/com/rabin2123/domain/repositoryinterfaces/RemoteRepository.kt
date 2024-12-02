package com.rabin2123.domain.repositoryinterfaces

interface RemoteRepository {
    /**
     * get info about hash of file
     *
     * @param hashSample hash of file
     * @return exists/ not exists
     */
    suspend fun getInfoAboutHashFile(hashSample: String): Boolean
}