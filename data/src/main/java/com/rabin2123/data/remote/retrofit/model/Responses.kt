package com.rabin2123.data.remote.retrofit.model

import com.google.gson.annotations.SerializedName

data class BazaarResponse(
    @SerializedName("query_status")
    val queryStatus: String
)