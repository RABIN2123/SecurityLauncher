package com.rabin2123.data.local.sharedprefs.encryptionprefs

interface EncryptionPrefs {
    /**
     * get aes key alias for encryption/decryption
     *
     * @return aes key alias
     */
    suspend fun getAesKeyAlias(): String

    /**
     * get key for vector initialization
     *
     * @return key for vector initialization
     */
    suspend fun getKeyIv(): ByteArray?

    /**
     * set key for vector initialization
     *
     * @param iv key for vector initialization
     */
    suspend fun setKeyIv(iv: ByteArray)
}