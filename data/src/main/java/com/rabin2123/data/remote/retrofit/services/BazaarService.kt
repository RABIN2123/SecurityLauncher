package com.rabin2123.data.remote.retrofit.services

import com.rabin2123.data.remote.retrofit.models.BazaarApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BazaarService {
    /**
     * get info about a file with the hash
     *
     * @param query type of query
     * @param hash hash of file for compare
     * @return info about file with hash
     */
    @FormUrlEncoded
    @POST("api/v1/")
    suspend fun compareHash(
        @Field("query") query: String = "get_info",
        @Field("hash") hash: String
    ): BazaarApiResponse
}