package com.rabin2123.domain.di

import com.rabin2123.data.di.dataModule
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForAdmin
import com.rabin2123.domain.repositoryimples.LocalRepositoryImpl
import com.rabin2123.domain.repositoryimples.RemoteRepositoryImpl
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForUser
import com.rabin2123.domain.repositoryinterfaces.RemoteRepository
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)
    factory<LocalRepositoryForAdmin> {
        LocalRepositoryImpl(localDataForUser = get(), localDataForAdmin = get())
    }
    factory<LocalRepositoryForUser> {
        get<LocalRepositoryImpl>()
    }
    factory<RemoteRepository> {
        RemoteRepositoryImpl(remoteData = get())
    }
}