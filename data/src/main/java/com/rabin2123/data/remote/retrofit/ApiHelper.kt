package com.rabin2123.data.remote.retrofit

import com.rabin2123.data.remote.retrofit.model.BazaarResponse

interface ApiHelper {
    suspend fun compareHash(hashFile: String): BazaarResponse
}