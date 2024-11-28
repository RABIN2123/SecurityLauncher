package com.rabin2123.data.local.sharedprefs.encryptionprefs

interface EncryptionPrefs {
    suspend fun getAesKeyAlias(): String
    suspend fun getKeyIv(): ByteArray?
    suspend fun setKeyIv(iv: ByteArray)
}