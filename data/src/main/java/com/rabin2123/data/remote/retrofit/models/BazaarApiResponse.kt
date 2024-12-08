package com.rabin2123.data.remote.retrofit.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * data class for response from https://bazaar.abuse.ch/
 *
 * @property queryStatus result if there is a file with hash on site
 */
@Keep
data class BazaarApiResponse(
    @SerializedName("query_status")
    val queryStatus: String
)