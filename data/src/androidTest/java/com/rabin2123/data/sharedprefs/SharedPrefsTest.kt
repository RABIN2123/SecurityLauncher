package com.rabin2123.data.sharedprefs

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.rabin2123.data.encryption.EncryptionBuilder
import com.rabin2123.data.encryption.helper.EncryptionHelperImpl
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefs
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefsBuilder
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefsImpl
import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefsBuilder
import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefsImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.security.SecureRandom

@RunWith(AndroidJUnit4::class)
class SharedPrefsTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val encryptedPrefs = EncryptionPrefsBuilder.getEncryptionPrefs(context)
    private val sharedPrefs = context.getSharedPreferences("encryption_prefs", Context.MODE_PRIVATE)


    @Test
    fun reading_encrypted_data_succeeded() {
        val securedPrefs = EncryptionPrefsImpl(encryptedPrefs)
        val testBytes = ByteArray(32)
        SecureRandom().nextBytes(testBytes)
        runBlocking {
            securedPrefs.setKeyIv(testBytes)
            assertThat(testBytes).isEqualTo(securedPrefs.getKeyIv())
        }
    }
    @Test(expected = NullPointerException::class)
    fun reading_encrypted_data_from_encryption_prefs_failed() {
        val securedPrefs: EncryptionPrefs = EncryptionPrefsImpl(encryptedPrefs)
        val commonPrefs: EncryptionPrefs = EncryptionPrefsImpl(sharedPrefs)
        assertThat(securedPrefs.getAesKeyAlias()).isNotEqualTo(commonPrefs.getAesKeyAlias())
        throw NullPointerException()
    }


    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val encPrefs = EncryptionPrefsImpl(encryptedPrefs)
    private val encryptionUtils = EncryptionBuilder(
        prefs = encPrefs,
        scope = scope
    )
    private val encryption = EncryptionHelperImpl(
        encryptUtil = encryptionUtils
    )
    private val encSettingsPrefs = SettingsPrefsBuilder.getSettingsPrefs(
        context = context,
        encryption = encryption,
        scope = scope
    )
    private val commonSettingsPrefs =
        context.getSharedPreferences("launcher_settings_prefs", Context.MODE_PRIVATE)

    @Test(expected = NullPointerException::class)
    fun reading_settings_prefs_failed() {
        val encryptedSettings = SettingsPrefsImpl(encSettingsPrefs, encryption)
        val commonSettings = SettingsPrefsImpl(commonSettingsPrefs, encryption)
        runBlocking {
            assertThat(encryptedSettings.getAdminPassword()).isNotEqualTo(commonSettings.getAdminPassword())
        }

        throw NullPointerException()
    }

    @Test
    fun reading_settings_prefs_succeeded() {
        val encryptedSettings = SettingsPrefsImpl(encSettingsPrefs, encryption)
        runBlocking {
            val password = ByteArray(32)
            SecureRandom().nextBytes(password)
            encryptedSettings.setAdminPassword(password)
            assertThat(password).isEqualTo(encryptedSettings.getAdminPassword())
        }
    }

    @After
    fun clearData() {

        context.deleteSharedPreferences("encryption_prefs")
        context.deleteSharedPreferences("launcher_settings_prefs")
    }
}