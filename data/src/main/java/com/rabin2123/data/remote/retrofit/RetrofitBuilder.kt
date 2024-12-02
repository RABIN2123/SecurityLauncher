package com.rabin2123.data.remote.retrofit

import com.rabin2123.data.remote.retrofit.services.BazaarService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object for builder for Retrofit with const URL
 */
object RetrofitBuilder {
    private const val BASE_URL = "https://mb-api.abuse.ch/"

    /**
     * build retrofit for api https://mb-api.abuse.ch/
     *
     * @return retrofit instance
     */
    private fun getRetrofit() =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    val bazaarService: BazaarService = getRetrofit().create(BazaarService::class.java)
}