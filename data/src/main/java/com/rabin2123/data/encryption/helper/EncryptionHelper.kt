package com.rabin2123.data.encryption.helper

import java.io.InputStream

interface EncryptionHelper {
    /**
     * encrypt new or input password
     *
     * @param password
     * @return encrypted password
     */
    fun encryptionPassword(password: String): ByteArray
    fun encryptionPassword(password: ByteArray): ByteArray

    /**
     * decrypt password
     * NOT USED
     *
     * @param password encrypted password
     * @return decrypted password
     */
    fun decryptionPassword(password: ByteArray): String
    fun decryptionPassword(inputStream: InputStream): ByteArray
}