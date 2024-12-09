package com.rabin2123.data.room

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import android.content.Context
import androidx.room.Room
import com.rabin2123.data.encryption.EncryptionBuilder
import com.rabin2123.data.encryption.helper.EncryptionHelperImpl
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppDatabaseBuilder
import com.google.common.truth.Truth.assertThat
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppEntity
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListDatabase
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefsBuilder
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefsImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    @Before
    fun beforeDb() {
        deleteDb()
    }
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val prefs = EncryptionPrefsBuilder.getEncryptionPrefs(context)
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val encPrefs = EncryptionPrefsImpl(prefs)
    private val encryptionUtils = EncryptionBuilder(
        prefs = encPrefs,
        scope = scope
    )
    private val encryption = EncryptionHelperImpl(
        encryptUtil = encryptionUtils
    )
    private val securedDb = AllowedAppDatabaseBuilder.getDatabaseAllowedAppList(
        context = context,
        scope = scope,
        encryption = encryption
    )

    @Test
    fun reading_of_encrypted_data_without_key_failed() {

        val commonDb =
            Room.databaseBuilder(context, AllowedAppListDatabase::class.java, "database.db")
                .build().dao
        runBlocking {
            val correctApp = securedDb.dao.getAllowedApps().firstOrNull()?.firstOrNull()
            commonDb.getAllowedApps().firstOrNull()?.forEach { app ->
                if (correctApp?.packageName == app.packageName) {
                    assertThat(true).isFalse()
                    return@runBlocking
                }
            }
            assertThat(false).isFalse()
        }
    }

    @Test
    fun reading_of_decrypted_data_succeeded() {
        val securedDbDao = securedDb.dao
        val testItem = AllowedAppEntity("TestApp", "TestApp")
        runBlocking {
            securedDb.dao.insertAllowedApps(listOf(testItem))
            securedDbDao.getAllowedApps().firstOrNull()?.forEach { value ->
                if (value == testItem) {
                    assertThat(true).isTrue()
                    return@runBlocking
                }
            }
            assertThat(false).isTrue()
        }
    }

    @After
    fun deleteDb() {
        context.deleteDatabase("database.db")
        context.deleteSharedPreferences("encryption_prefs.xml")
        context.deleteFile("RoomDb")
    }

}