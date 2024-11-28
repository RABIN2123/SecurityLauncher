package com.rabin2123.data.encryption.helper

interface EncryptionHelper {
    suspend fun encryptionPassword(password: String): ByteArray
    suspend fun decryptionPassword(password: ByteArray): String
}