package com.rabin2123.data.remote.retrofit

import android.util.Log
import com.rabin2123.data.remote.retrofit.models.BazaarApiResponse
import com.rabin2123.data.remote.retrofit.services.BazaarService

class ApiHelperImpl(private val bazaarService: BazaarService): ApiHelper {
    override suspend fun compareHash(hashFile: String): BazaarApiResponse {
        Log.d("TAG!","ApiHelperImpl/compareHash")
        return bazaarService.compareHash(hash = hashFile)
    }
}