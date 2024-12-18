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

    /**
     * Repository for local all data
     */
    factory {
        LocalRepositoryImpl(localDataForUser = get(), localDataForAdmin = get())
    }

    /**
     * Repository for local data with admin access
     */
    factory<LocalRepositoryForAdmin> {
        get<LocalRepositoryImpl>()
    }

    /**
     * Repository for local data with user access
     */
    factory<LocalRepositoryForUser> {
        get<LocalRepositoryImpl>()
    }
    /**
     * Repository for remote data
     */
    factory<RemoteRepository> {
        RemoteRepositoryImpl(remoteData = get())
    }
}