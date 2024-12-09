package com.rabin2123.data.encryption

import java.io.InputStream

interface AesEncryption {
    /**
     * Encrypts password
     *
     * @param password decrypted password for admin
     * @return encrypted password
     */
    fun encrypt(password: String): ByteArray

    fun encrypt(password: ByteArray): ByteArray
    /**
     * Decrypts password
     *
     * @param encryptedPassword encrypted password for admin
     * @return decrypted password for admin
     */
    fun decrypt(encryptedPassword: ByteArray): String

    fun decrypt(inputStream: InputStream): ByteArray
}