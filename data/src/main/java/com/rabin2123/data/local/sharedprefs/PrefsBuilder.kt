package com.rabin2123.data.local.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.rabin2123.data.local.sharedprefs.models.SettingsData

object PrefsBuilder {
    fun getSettingsPrefs(context: Context): SharedPreferences {
        val masterKeyAlias =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val prefs = EncryptedSharedPreferences.create(
            context,
            SettingsData.PREFS_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        if (!prefs.contains(SettingsData.ADMIN_PASSWORD)) {
            prefs[SettingsData.ADMIN_PASSWORD] = "0000"
            prefs[SettingsData.ML_BAZAAR] = false
            prefs[SettingsData.BLOCK_SETTINGS] = false
            prefs[SettingsData.BLOCK_GPS] = false
            prefs[SettingsData.BLOCK_USB] = false
            prefs[SettingsData.BLOCK_CAMERA] = false
        }
        return prefs
    }


}