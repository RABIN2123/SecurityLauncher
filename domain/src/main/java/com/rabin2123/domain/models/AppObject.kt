package com.rabin2123.domain.models

import com.rabin2123.data.local.globalapplist.models.AppObjectData
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * data class for app on phone
 *
 * @property name app name
 * @property packageName app package name
 */
data class AppObject(
    val name: String,
    val packageName: String
)

/**
 * conversion List<AppObjectData> to List<AppObject>
 *
 * @return same data but with List<AppObject> of type
 */
fun List<AppObjectData>.toDomain(): List<AppObject> {
    return this.map { data ->
        AppObject(
            name = data.name,
            packageName = data.packageName
        )
    }
}

/**
 * conversion Flow<List<AllowedAppEntity>> to Flow<List<AppObject>>
 *
 * @return same data but with Flow<List<AppObject>> of type
 */
fun Flow<List<AllowedAppEntity>>.toDomain(): Flow<List<AppObject>> {
    return this.map { flow ->
        flow.map { data ->
            AppObject(
                name = data.appName,
                packageName = data.packageName
            )
        }
    }
}

/**
 * conversion List<appObject> to List<AllowedAppEntity>
 *
 * @return same data but with List<AllowedAppEntity>
 */
fun List<AppObject>.toData(): List<AllowedAppEntity> {
    return this.map { data ->
        AllowedAppEntity(
            packageName = data.packageName,
            appName = data.name
        )
    }
}