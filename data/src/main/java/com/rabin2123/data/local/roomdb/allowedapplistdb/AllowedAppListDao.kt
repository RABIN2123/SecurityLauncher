package com.rabin2123.data.local.roomdb.allowedapplistdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AllowedAppListDao {
    /**
     * insert list with allowed app list
     *
     * @param allowedAppList list with allowed app for user
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllowedApps(allowedAppList: List<AllowedAppEntity>)

    /**
     * get allowed app list ordered by app name
     *
     * @return list with allowed app for user
     */
    @Query("SELECT * FROM ${AllowedAppEntity.TABLE_NAME} ORDER BY ${AllowedAppEntity.COLUMN_APP_NAME}")
    fun getAllowedApps(): Flow<List<AllowedAppEntity>>

    /**
     * delete selected apps from allowed app list
     *
     * @param allowedAppList list with allowed apps for user that need to be delete
     */
    @Delete
    suspend fun deleteAllowedApps(allowedAppList: List<AllowedAppEntity>)

    /**
     * delete all apps from allowed app list except launcher settings
     *
     */
    @Query("DELETE FROM ${AllowedAppEntity.TABLE_NAME} WHERE ${AllowedAppEntity.COLUMN_APP_PACKAGE} != 'launcher_settings'")
    suspend fun deleteAllAllowedApps()
}