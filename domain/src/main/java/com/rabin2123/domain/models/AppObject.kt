package com.rabin2123.domain.models

import com.rabin2123.data.local.applist.models.AppObjectData

data class AppObject(
    val name: String,
    val packageName: String
)

fun List<AppObjectData>.toDomain(): List<AppObject> {
    return this.map { data ->
        AppObject(
            name = data.name,
            packageName = data.packageName
        )
    }
}