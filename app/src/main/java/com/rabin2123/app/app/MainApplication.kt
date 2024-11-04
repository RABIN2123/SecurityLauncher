package com.rabin2123.app.app

import android.app.Application
import com.rabin2123.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.androix.startup.KoinStartup.onKoinStartup
import org.koin.core.annotation.KoinExperimentalAPI


class MainApplication: Application() {

    init {
        @OptIn(KoinExperimentalAPI::class)
        onKoinStartup {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(appModule))
        }
    }
}