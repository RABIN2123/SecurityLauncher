package com.rabin2123.data.local.roomdb

import android.content.Context
import androidx.room.Room
import com.rabin2123.data.local.roomdb.allowedapplistdb.AllowedAppListDatabase

object DatabaseBuilder {
    private var INSTANCE: AllowedAppListDatabase? = null
    fun getDatabaseAllowedAppList(context: Context) = INSTANCE ?: synchronized(this) {
        val instance =
            Room.databaseBuilder(
                context,
                AllowedAppListDatabase::class.java,
                "database.db").build()
        INSTANCE = instance
        instance
    }
    // TODO не хватает sqlcipher!!!
}