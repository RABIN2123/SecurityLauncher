package com.rabin2123.data.local.roomdb.allowedapplistdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AllowedAppListDao {
    @Insert
    suspend fun insertAllowedApps(allowedAppList: List<AllowedAppListEntity>)

    @Query("SELECT * FROM ${AllowedAppListEntity.TABLE_NAME} ORDER BY ${AllowedAppListEntity.COLUMN_APP_NAME}")
    fun getAllowedApps(): Flow<List<AllowedAppListEntity>>

    @Delete
    fun deleteAllowedApps(allowedAppList: List<AllowedAppListEntity>)
}