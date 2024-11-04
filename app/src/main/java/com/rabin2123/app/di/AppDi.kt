package com.rabin2123.app.di

import com.rabin2123.domain.di.domainModule
import com.rabin2123.app.gridapp.GridAppViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    includes(domainModule)
    viewModel<GridAppViewModel> {
        GridAppViewModel(Repository = get())
    }
}