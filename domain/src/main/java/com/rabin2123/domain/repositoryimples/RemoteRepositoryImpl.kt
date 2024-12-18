package com.rabin2123.domain.repositoryimples

import android.util.Log
import com.rabin2123.data.remote.retrofit.ApiHelper
import com.rabin2123.domain.repositoryinterfaces.RemoteRepository

class RemoteRepositoryImpl(private val remoteData: ApiHelper): RemoteRepository {
    override suspend fun getInfoAboutHashFile(hashSample: String): Boolean {
        Log.d("TAG!", "RemoteRepositoryImpl/getInfoAboutHashFile: $hashSample")
        return remoteData.compareHash(hashFile = hashSample).queryStatus == "ok"
    }
}