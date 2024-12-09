package com.rabin2123.data.local.roomdb.allowedapplistdb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rabin2123.data.encryption.helper.EncryptionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.sqlcipher.database.SupportFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom

object AllowedAppDatabaseBuilder {
    @Volatile
    private var INSTANCE: AllowedAppListDatabase? = null

    /**
     * create or get database for allowed app list for user
     *
     * @param context context
     * @param scope coroutine scope
     * @param encryption encryption from EncryptionHelper
     *
     * @return database instance
     */
    fun getDatabaseAllowedAppList(
        context: Context,
        scope: CoroutineScope,
        encryption: EncryptionHelper
    ) =
        INSTANCE ?: synchronized(this) {
            val instance = Room
                .databaseBuilder(
                    context,
                    AllowedAppListDatabase::class.java,
                    "database.db"
                )
                .openHelperFactory(SupportFactory(getPassword(context = context,encryption = encryption)))
                .addCallback(AllowedAppListDatabaseCallback(scope))
                .build()
            INSTANCE = instance
            instance
        }
    // TODO не хватает sqlcipher!!!
    /**
     * callback for filling the database with allowed app list
     *
     * @property scope coroutine scope
     */
    private class AllowedAppListDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        /**
         * filling database after her created
         *
         * @param db
         */
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

    private fun getPassword(
        context: Context,
        encryption: EncryptionHelper
    ): ByteArray {
        val file = File(context.filesDir, "RoomDb")
        return if (file.exists()) {
            encryption.decryptionPassword(FileInputStream(file))
        } else {
            val password = generatePassword()
            FileOutputStream(file).use { stream ->
                stream.write(encryption.encryptionPassword(password))
            }
            password
        }
    }

    private fun generatePassword(): ByteArray {
        val result = ByteArray(32)

        SecureRandom().nextBytes(result)

        while (result.contains(0)) {
            SecureRandom().nextBytes(result)
        }

        return result
    }
}