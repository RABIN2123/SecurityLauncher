package com.rabin2123.data.encryption.helper

import com.rabin2123.data.encryption.AesEncryption
import java.io.InputStream

class EncryptionHelperImpl(private val encryptUtil: AesEncryption) : EncryptionHelper {
    override fun encryptionPassword(password: String): ByteArray {
        return encryptUtil.encrypt(password)
    }

    override fun encryptionPassword(password: ByteArray): ByteArray {
        return encryptUtil.encrypt(password)
    }

    override fun decryptionPassword(password: ByteArray): String {
        return encryptUtil.decrypt(password)
    }

    override fun decryptionPassword(inputStream: InputStream): ByteArray {
        return encryptUtil.decrypt(inputStream)
    }
}