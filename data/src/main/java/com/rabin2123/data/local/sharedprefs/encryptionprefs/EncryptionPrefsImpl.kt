package com.rabin2123.data.local.sharedprefs.encryptionprefs

import android.content.SharedPreferences
import com.rabin2123.data.local.sharedprefs.get
import com.rabin2123.data.local.sharedprefs.set


class EncryptionPrefsImpl(private val prefs: SharedPreferences): EncryptionPrefs {
    override fun getAesKeyAlias(): String {
        return prefs[EncryptionPrefsBuilder.AES_KEY_ALIAS]
    }

    override fun getKeyIv(): ByteArray? {
        return prefs[EncryptionPrefsBuilder.IV_KEY]
    }

    override suspend fun setKeyIv(iv: ByteArray) {
        prefs[EncryptionPrefsBuilder.IV_KEY] = iv
    }
}