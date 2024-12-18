package com.rabin2123.data.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProperties.BLOCK_MODE_CBC
import android.util.Base64
import android.util.Log
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

private const val IV_SIZE = 16

class EncryptionBuilder(
    private val prefs: EncryptionPrefs,
    private val scope: CoroutineScope
) : AesEncryption {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }
    private var aesKeyAlias = prefs.getAesKeyAlias()


    /**
     * encrypt data
     *
     * @param password decrypted password
     * @return
     */
    override fun encrypt(password: String): ByteArray {
        return encrypt(password.toBase64())
    }

    override fun encrypt(password: ByteArray): ByteArray {
        val key = getKey()
        val iv = getIv()
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "AndroidKeyStoreBCWorkaround")
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        return cipher.doFinal(password)
    }

    /**
     * get or create secret key
     *
     * @return secret key
     */
    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(aesKeyAlias, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    /**
     * create secret key
     *
     * @return secret key
     */
    private fun createKey(): SecretKey {
        Log.d("TAG!", "createKey")
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

    /**
     * get initialization vector
     *
     * @return vector initialization
     */
    private fun getIv(): IvParameterSpec {
        val ivPrefs: ByteArray? = prefs.getKeyIv()
        return if (ivPrefs != null) {
            IvParameterSpec(ivPrefs)
        } else {
            Log.d("TAG!", "Generate IV")
            val generatedIv = generateIv()
            scope.launch {
                prefs.setKeyIv(generatedIv.iv)
            }
            generatedIv
        }
    }

    /**
     * create initialization vector
     *
     * @return initialization vector
     */
    private fun generateIv(): IvParameterSpec {
        val ivBytes = ByteArray(IV_SIZE)
        SecureRandom().nextBytes(ivBytes)
        return IvParameterSpec(ivBytes)
    }

    /**
     * decrypt data
     *
     * @param encryptedPassword encrypted password
     * @return decrypted password
     */
    override fun decrypt(encryptedPassword: ByteArray): String {
        val key = getKey()
        val iv = getIv()
        val cipher = Cipher.getInstance(TRANSFORMATION, "AndroidKeyStoreBCWorkaround")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        return cipher.doFinal(encryptedPassword).encodeToString()
    }

    override fun decrypt(inputStream: InputStream): ByteArray {
        val key = getKey()
        val iv = getIv()
        val cipher = Cipher.getInstance(TRANSFORMATION, "AndroidKeyStoreBCWorkaround")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        return inputStream.use { stream ->
            val encryptedBytes = ByteArray(size = stream.read())
            stream.read(encryptedBytes)
            cipher.doFinal(encryptedBytes)
        }
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }


}

/**
 * conversion string to byteArray
 *
 * @return byteArray
 */
internal fun String.toBase64(): ByteArray {
    return Base64.decode(this, Base64.NO_WRAP or Base64.NO_PADDING)
}

/**
 * conversion byteArray to string
 *
 * @return string
 */
fun ByteArray.encodeToString(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP or Base64.NO_PADDING)
}