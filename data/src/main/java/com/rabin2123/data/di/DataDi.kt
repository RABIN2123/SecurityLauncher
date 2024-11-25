package com.rabin2123.data.di

import com.rabin2123.data.local.globalapplist.GlobalAppListData
import com.rabin2123.data.local.globalapplist.GlobalAppListDataImpl
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListDao
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelper
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListHelperImpl
import com.rabin2123.data.local.roomdb.DatabaseBuilder
import com.rabin2123.data.remote.retrofit.ApiHelper
import com.rabin2123.data.remote.retrofit.ApiHelperImpl
import com.rabin2123.data.remote.retrofit.services.BazaarService
import com.rabin2123.data.remote.retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val dataModule = module {
    single<GlobalAppListData> {
        GlobalAppListDataImpl(context = get())
    }
    single<BazaarService> {
        RetrofitBuilder.bazaarService
    }
    single<ApiHelper> {
        ApiHelperImpl(bazaarService = get())
    }
    single<AllowedAppListDao> {
        DatabaseBuilder.getDatabaseAllowedAppList(
            context = get(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        ).dao
    }
    single<AllowedAppListHelper> {
        AllowedAppListHelperImpl(dao = get())
    }
}