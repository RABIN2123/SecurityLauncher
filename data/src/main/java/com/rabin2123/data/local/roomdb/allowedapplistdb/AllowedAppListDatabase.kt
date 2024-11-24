package com.rabin2123.data.local.roomdb.allowedapplistdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AllowedAppListEntity::class], version = 1)
abstract class AllowedAppListDatabase: RoomDatabase() {
    abstract val dao: AllowedAppListDao
}