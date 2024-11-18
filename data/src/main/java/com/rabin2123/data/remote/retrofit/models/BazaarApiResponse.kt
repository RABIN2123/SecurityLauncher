package com.rabin2123.data.remote.retrofit.models

import com.google.gson.annotations.SerializedName

data class BazaarApiResponse(
    @SerializedName("query_status")
    val queryStatus: String
)