package com.rabin2123.domain.models

import com.rabin2123.data.local.globalapplist.models.AppObjectData
import com.rabin2123.data.local.roomdb.AllowedAppListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

fun Flow<List<AllowedAppListEntity>>.toDomain(): Flow<List<AppObject>> {
    return this.map { flow ->
        flow.map { data ->
            AppObject(
                name = data.appName,
                packageName = data.packageName
            )
        }
    }
}

fun List<AppObject>.toData(): List<AllowedAppListEntity> {
    return this.map { data ->
        AllowedAppListEntity(
            packageName = data.packageName,
            appName = data.name
        )
    }
}