package com.rabin2123.data.encryption.helper

interface EncryptionHelper {
    /**
     * encrypt new or input password
     *
     * @param password
     * @return encrypted password
     */
    suspend fun encryptionPassword(password: String): ByteArray

    /**
     * decrypt password
     * NOT USED
     *
     * @param password encrypted password
     * @return decrypted password
     */
    suspend fun decryptionPassword(password: ByteArray): String
}