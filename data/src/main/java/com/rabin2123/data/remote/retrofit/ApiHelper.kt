package com.rabin2123.data.remote.retrofit

import com.rabin2123.data.remote.retrofit.models.BazaarApiResponse

interface ApiHelper {
    /**
     * Send hash of file to malwareBazaar API and return response
     *
     * @param hashFile hash of file for compare
     * @return response from malwareBazaar
     */
    suspend fun compareHash(hashFile: String): BazaarApiResponse
}