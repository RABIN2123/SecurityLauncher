package com.rabin2123.data.local.roomdb.allowedapplistdb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object AllowedAppDatabaseBuilder {
    @Volatile
    private var INSTANCE: AllowedAppListDatabase? = null
    fun getDatabaseAllowedAppList(context: Context, scope: CoroutineScope) =
        INSTANCE ?: synchronized(this) {
            val instance = Room
                .databaseBuilder(
                    context,
                    AllowedAppListDatabase::class.java,
                    "database.db"
                )
                .addCallback(AllowedAppListDatabaseCallback(scope))
                .build()
            INSTANCE = instance
            instance
        }
    // TODO не хватает sqlcipher!!!

    private class AllowedAppListDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val dao = database.dao
                    dao.insertAllowedApps(
                        listOf(
                            AllowedAppEntity(
                                packageName = "launcher_settings",
                                appName = "Security settings"
                            )
                        )
                    )
                }
            }
        }
    }
}