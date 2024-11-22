package com.rabin2123.data.local.roomdb

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    fun getDatabase(context: Context) = synchronized(this) {
        Room.databaseBuilder(context, AllowedAppListDatabase::class.java, "database.db").build().dao
    }
    // TODO не хватает sqlcipher!!!
}