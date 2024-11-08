package com.rabin2123.data.remote.retrofit.services

import com.rabin2123.data.remote.retrofit.model.BazaarResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BazaarService {
    @FormUrlEncoded
    @POST("api/v1/")
    suspend fun compareHash(
        @Field("query") query: String = "get_info",
        @Field("hash") hash: String
    ): BazaarResponse
}