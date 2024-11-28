package com.rabin2123.data.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProperties.BLOCK_MODE_CBC
import android.util.Base64
import com.rabin2123.data.encryption.prefs.EncryptionDataPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

private const val IV_SIZE = 16

class EncryptionBuilder(
    private val prefs: EncryptionDataPrefs,
    private val scope: CoroutineScope
): AesEncryption {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }
    private var aesKeyAlias = ""
    private var ivBytes: ByteArray? = null

    init {
        scope.launch {
            aesKeyAlias = prefs.getAesKeyAlias()
            ivBytes = prefs.getKeyIv().toBase64()
        }
    }


    override suspend fun encrypt(password: String): ByteArray {
        val key = getKey()
        val iv = getIv()
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "AndroidKeyStoreBCWorkaround")
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        return cipher.doFinal(password.toBase64())
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(aesKeyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    aesKeyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE_CBC)
                    .setKeySize(256)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(false)
                    .build()
            )
        }.generateKey()
    }

    private fun getIv(): IvParameterSpec {
        return if (ivBytes != null) {
            IvParameterSpec(ivBytes)
        } else {
            ivBytes = ByteArray(IV_SIZE)
            SecureRandom().nextBytes(ivBytes)
            scope.launch {
                prefs.setKeyIv(ivBytes!!.encodeToString())
            }

            IvParameterSpec(ivBytes)
        }
    }

    override suspend fun decrypt(encryptPassword: ByteArray): String {
        val key = getKey()
        val iv = getIv()
        val cipher = Cipher.getInstance(TRANSFORMATION, "AndroidKeyStoreBCWorkaround")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        return cipher.doFinal(encryptPassword).encodeToString()
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }


}

internal fun String.toBase64(): ByteArray {
    return Base64.decode(this, Base64.NO_WRAP or Base64.NO_PADDING)
}

internal fun ByteArray.encodeToString(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP or Base64.NO_PADDING)
}