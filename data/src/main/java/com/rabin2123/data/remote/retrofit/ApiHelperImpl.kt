package com.rabin2123.data.remote.retrofit

import android.util.Log
import com.rabin2123.data.remote.retrofit.model.BazaarResponse
import com.rabin2123.data.remote.retrofit.services.BazaarService

class ApiHelperImpl(private val bazaarService: BazaarService): ApiHelper {
    override suspend fun compareHash(hashFile: String): BazaarResponse {
        Log.d("TAG!", "HASH: $hashFile")
        return bazaarService.compareHash(hash = hashFile)
    }
}