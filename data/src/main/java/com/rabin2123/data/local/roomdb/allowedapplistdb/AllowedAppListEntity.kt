package com.rabin2123.data.local.roomdb.allowedapplistdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = AllowedAppListEntity.TABLE_NAME)
data class AllowedAppListEntity (
    @PrimaryKey
    @ColumnInfo(name = COLUMN_APP_PACKAGE) val packageName: String,
    @ColumnInfo(name = COLUMN_APP_NAME) val appName: String
) {
    companion object {
        const val TABLE_NAME = "allowed_app_list"
        const val COLUMN_APP_PACKAGE = "app_package"
        const val COLUMN_APP_NAME = "app_name"
    }
}

