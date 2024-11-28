package com.rabin2123.data.di

import android.content.SharedPreferences
import com.rabin2123.data.encryption.AesEncryption
import com.rabin2123.data.encryption.EncryptionBuilder
import com.rabin2123.data.encryption.helper.EncryptionHelper
import com.rabin2123.data.encryption.helper.EncryptionHelperImpl
import com.rabin2123.data.encryption.prefs.EncryptionDataPrefs
import com.rabin2123.data.encryption.prefs.EncryptionDataPrefsImpl
import com.rabin2123.data.local.globalapplist.GlobalAppListData
import com.rabin2123.data.local.globalapplist.GlobalAppListDataImpl
import com.rabin2123.data.local.LocalDataForAdmin
import com.rabin2123.data.local.LocalDataForAdminImpl
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListDao
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelper
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelperImpl
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppDatabaseBuilder
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
import org.koin.dsl.module

val dataModule = module {
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
     * Use for send sample of file to https://bazaar.abuse.ch/
     */
    single<BazaarService> {
        RetrofitBuilder.bazaarService
    }
    single<ApiHelper> {
        ApiHelperImpl(bazaarService = get())
    }

    /**
     * Allowed app list database for simple user
     */
    single<AllowedAppListDao> {
        AllowedAppDatabaseBuilder.getDatabaseAllowedAppList(
            context = get(),
            scope = get(),
            encryption = get()
        ).dao
    }
    single<AllowedAppListHelper> {
        AllowedAppListHelperImpl(dao = get())
    }

    /**
     * Shared preferences with launcher settings
     */
    single<SharedPreferences> {
        SettingsPrefsBuilder.getSettingsPrefs(
            context = get(),
            encryption = get(),
            scope = get()
        )
    }
    single<SettingsPrefs> {
        SettingsPrefsImpl(
            prefs = get(),
            encryption = get()
        )
    }

    /**
     * Global app list + launcher settings database functions
     */
    single<LocalDataForAdmin> {
        LocalDataForAdminImpl(
            globalAppList = get(),
            settingsDb = get()
        )
    }

    /**
     * Encryption and get aes key alias
     */
    single<EncryptionDataPrefs> {
        EncryptionDataPrefsImpl(
            settingsDb = get()
        )
    }
    single<AesEncryption> {
        EncryptionBuilder(
            prefs = get(),
            scope = get()
        )
    }

    single<EncryptionHelper> {
        EncryptionHelperImpl(
            encryptUtil = get()
        )
    }

}