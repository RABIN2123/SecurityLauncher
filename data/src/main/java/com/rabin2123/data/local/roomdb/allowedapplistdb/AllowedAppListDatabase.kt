package com.rabin2123.data.local.roomdb.allowedapplistdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AllowedAppEntity::class], version = 1, exportSchema = false)
abstract class AllowedAppListDatabase: RoomDatabase() {
    abstract val dao: AllowedAppListDao
}