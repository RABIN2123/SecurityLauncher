package com.rabin2123.domain.di

import com.rabin2123.data.di.dataModule
import com.rabin2123.domain.repositoryinterfaces.LocalRepository
import com.rabin2123.domain.repositoryimples.LocalRepositoryImpl
import com.rabin2123.domain.repositoryimples.RemoteRepositoryImpl
import com.rabin2123.domain.repositoryinterfaces.RemoteRepository
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)
    factory<LocalRepository> {
        LocalRepositoryImpl(localData = get())
    }
    factory<RemoteRepository> {
        RemoteRepositoryImpl(remoteData = get())
    }
}