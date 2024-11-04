package com.rabin2123.domain

import com.rabin2123.domain.models.AppObject

interface Repository {
    suspend fun getAllAppList(): List<AppObject>
}