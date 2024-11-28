package com.rabin2123.data.encryption.prefs

interface EncryptionDataPrefs {
    suspend fun getAesKeyAlias(): String
    suspend fun getKeyIv(): String
    suspend fun setKeyIv(iv: String)
}