package com.rabin2123.data.local.roomdb.allowedapplistdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AllowedAppListDao {
    @Insert
    suspend fun insertAllowedApps(allowedAppList: List<AllowedAppEntity>)

    @Query("SELECT * FROM ${AllowedAppEntity.TABLE_NAME} ORDER BY ${AllowedAppEntity.COLUMN_APP_NAME}")
    fun getAllowedApps(): Flow<List<AllowedAppEntity>>

    @Delete
    suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppEntity>)

    @Query("DELETE FROM ${AllowedAppEntity.TABLE_NAME} WHERE ${AllowedAppEntity.COLUMN_APP_PACKAGE} != 'launcher_settings'")
    suspend fun deleteAllAllowedApps()
}