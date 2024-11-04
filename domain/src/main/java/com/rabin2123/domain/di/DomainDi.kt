package com.rabin2123.domain.di

import com.rabin2123.data.di.dataModule
import com.rabin2123.domain.Repository
import com.rabin2123.domain.RepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)
    factory<Repository> {
        RepositoryImpl(localData = get())
    }
}