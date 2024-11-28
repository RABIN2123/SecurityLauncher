package com.rabin2123.data.encryption.helper

import com.rabin2123.data.encryption.AesEncryption

class EncryptionHelperImpl(private val encryptUtil: AesEncryption) : EncryptionHelper {
    override suspend fun encryptionPassword(password: String): ByteArray {
        return encryptUtil.encrypt(password)
    }

    override suspend fun decryptionPassword(password: ByteArray): String {
        return encryptUtil.decrypt(password)
    }
}