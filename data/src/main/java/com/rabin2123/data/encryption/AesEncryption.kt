package com.rabin2123.data.encryption

interface AesEncryption {
    suspend fun encrypt(password: String): ByteArray
    suspend fun decrypt(encryptPassword: ByteArray): String
}