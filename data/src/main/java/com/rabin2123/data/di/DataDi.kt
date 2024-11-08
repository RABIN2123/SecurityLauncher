package com.rabin2123.data.di

import com.rabin2123.data.local.applist.AppListData
import com.rabin2123.data.local.applist.AppListDataImpl
import com.rabin2123.data.remote.retrofit.ApiHelper
import com.rabin2123.data.remote.retrofit.ApiHelperImpl
import com.rabin2123.data.remote.retrofit.services.BazaarService
import com.rabin2123.data.remote.retrofit.RetrofitBuilder
import org.koin.dsl.module

val dataModule = module {
    single<AppListData> {
        AppListDataImpl(context = get())
    }
    single<BazaarService> {
        RetrofitBuilder.bazaarService
    }
    single<ApiHelper> {
        ApiHelperImpl(bazaarService = get())
    }
}