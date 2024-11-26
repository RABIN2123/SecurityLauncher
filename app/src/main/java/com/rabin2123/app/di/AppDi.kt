package com.rabin2123.app.di

import com.rabin2123.app.adminsettings.LauncherSettingsViewModel
import com.rabin2123.domain.di.domainModule
import com.rabin2123.app.gridapp.GridAppViewModel
import com.rabin2123.app.utils.AdminUtils
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    includes(domainModule)
    viewModel<GridAppViewModel> {
        GridAppViewModel(repository = get())
    }
    viewModel<LauncherSettingsViewModel> {
        LauncherSettingsViewModel(repository = get())
    }
    single<AdminUtils> { AdminUtils(context = get()) }
}