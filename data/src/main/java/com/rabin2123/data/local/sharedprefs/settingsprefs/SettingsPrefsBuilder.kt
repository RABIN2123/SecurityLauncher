package com.rabin2123.data.local.sharedprefs.settingsprefs

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.rabin2123.data.encryption.encodeToString
import com.rabin2123.data.encryption.helper.EncryptionHelper
import com.rabin2123.data.local.sharedprefs.settingsprefs.models.SettingsData
import com.rabin2123.data.local.sharedprefs.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SettingsPrefsBuilder {
    /**
     * get shared preferences instance with settings data
     *
     * @param context context
     * @param encryption encryption for encryption/decryption shared preferences
     * @param scope Coroutine scope
     * @return shared preferences instance with settings
     */
    fun getSettingsPrefs(
        context: Context,
        encryption: EncryptionHelper,
        scope: CoroutineScope
    ): SharedPreferences {
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
            scope.launch {
                val test = encryption.encryptionPassword("0000")
                Log.d("TAG!", "PASSWORD ${test.encodeToString()}")
                prefs[SettingsData.ADMIN_PASSWORD] = test
            }
            prefs[SettingsData.ML_BAZAAR] = false
            prefs[SettingsData.BLOCK_SETTINGS] = false
            prefs[SettingsData.BLOCK_GPS] = false
            prefs[SettingsData.BLOCK_USB] = false
            prefs[SettingsData.BLOCK_CAMERA] = false
        }
        return prefs
    }
}