package com.rabin2123.data.di

import android.content.SharedPreferences
import com.rabin2123.data.encryption.AesEncryption
import com.rabin2123.data.encryption.EncryptionBuilder
import com.rabin2123.data.encryption.helper.EncryptionHelper
import com.rabin2123.data.encryption.helper.EncryptionHelperImpl
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefs
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefsImpl
import com.rabin2123.data.local.globalapplist.GlobalAppListData
import com.rabin2123.data.local.globalapplist.GlobalAppListDataImpl
import com.rabin2123.data.local.LocalDataForAdmin
import com.rabin2123.data.local.LocalDataForAdminImpl
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListDao
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelper
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelperImpl
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppDatabaseBuilder
import com.rabin2123.data.local.sharedprefs.encryptionprefs.EncryptionPrefsBuilder
import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefs
import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefsBuilder
import com.rabin2123.data.local.sharedprefs.settingsprefs.SettingsPrefsImpl
import com.rabin2123.data.remote.retrofit.ApiHelper
import com.rabin2123.data.remote.retrofit.ApiHelperImpl
import com.rabin2123.data.remote.retrofit.services.BazaarService
import com.rabin2123.data.remote.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    /**
     * Coroutine for scope processes
     */
    factory<CoroutineScope> {
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    /**
     * List with all application on device
     */
    single<GlobalAppListData> {
        GlobalAppListDataImpl(context = get())
    }

    /**
     * Retrofit builder for API https://bazaar.abuse.ch/
     */
    single<BazaarService> {
        RetrofitBuilder.bazaarService
    }
    /**
     * Methods for work with API https://bazaar.abuse.ch/
     */
    single<ApiHelper> {
        ApiHelperImpl(bazaarService = get())
    }

    /**
     * DAO with SQLite query for room db with allowed app list for user
     */
    single<AllowedAppListDao> {
        AllowedAppDatabaseBuilder.getDatabaseAllowedAppList(
            context = get(),
            scope = get(),
            encryption = get()
        ).dao
    }
    /**
     * Builder room db for allowed app list for user
     */
    single<AllowedAppListHelper> {
        AllowedAppListHelperImpl(dao = get())
    }

    /**
     * Builder for shared preferences with launcher settings
     */
    single<SharedPreferences>(named(SETTINGS_PREFS)) {
        SettingsPrefsBuilder.getSettingsPrefs(
            context = get(),
            encryption = get(),
            scope = get()
        )
    }
    /**
     * Methods for work with shared preferences with launcher settings
     */
    single<SettingsPrefs> {
        SettingsPrefsImpl(
            prefs = get(named(SETTINGS_PREFS)),
            encryption = get()
        )
    }

    /**
     * Association methods for get global app list and control launcher settings
     */
    single<LocalDataForAdmin> {
        LocalDataForAdminImpl(
            globalAppList = get(),
            settingsDb = get()
        )
    }

    /**
     * Builder for shared preferences with encryption data (alias and iv)
     */
    single<SharedPreferences>(named(ENCRYPTION_PREFS)) {
        EncryptionPrefsBuilder.getEncryptionPrefs(context = get())
    }
    /**
     * Methods for work with encryption data
     */
    single<EncryptionPrefs> {
        EncryptionPrefsImpl(
            prefs = get(named(ENCRYPTION_PREFS))
        )
    }
    /**
     * Builder for encryption
     */
    single<AesEncryption> {
        EncryptionBuilder(
            prefs = get(),
            scope = get()
        )
    }
    /**
     * Methods for work with encryption (encrypt/decrypt password)
     */
    single<EncryptionHelper> {
        EncryptionHelperImpl(
            encryptUtil = get()
        )
    }
}

private const val SETTINGS_PREFS = "settingsPrefs"
private const val ENCRYPTION_PREFS = "encryptionPrefs"