package com.rabin2123.data.di

import com.rabin2123.data.local.applist.AppListData
import com.rabin2123.data.local.applist.AppListDataImpl
import org.koin.dsl.module

val dataModule = module {
    single<AppListData> {
        AppListDataImpl(context = get())
    }
}