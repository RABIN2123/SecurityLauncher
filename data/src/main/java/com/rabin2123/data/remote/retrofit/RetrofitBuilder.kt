package com.rabin2123.data.remote.retrofit

import com.rabin2123.data.remote.retrofit.services.BazaarService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://mb-api.abuse.ch/"
    private fun getRetrofit() =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    val bazaarService: BazaarService = getRetrofit().create(BazaarService::class.java)
}