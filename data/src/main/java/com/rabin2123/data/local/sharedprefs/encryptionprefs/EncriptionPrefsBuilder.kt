package com.rabin2123.data.local.sharedprefs.encryptionprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.rabin2123.data.local.sharedprefs.set

object EncryptionPrefsBuilder {
    fun getEncryptionPrefs(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val prefs = EncryptedSharedPreferences.create(
            context,
            ENCRYPTION_PREFS_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        if(!prefs.contains(AES_KEY_ALIAS)) {
            prefs[AES_KEY_ALIAS] = "some key"
        }
        return prefs
    }
    private const val ENCRYPTION_PREFS_NAME = "encryption_prefs"
    const val AES_KEY_ALIAS = "aes_key_alias"
    const val IV_KEY = "iv"
}