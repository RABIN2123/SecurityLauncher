package com.rabin2123.data.encryption

interface AesEncryption {
    /**
     * Encrypts password
     *
     * @param password decrypted password for admin
     * @return encrypted password
     */
    suspend fun encrypt(password: String): ByteArray
    /**
     * Decrypts password
     *
     * @param encryptedPassword encrypted password for admin
     * @return decrypted password for admin
     */
    suspend fun decrypt(encryptedPassword: ByteArray): String
}