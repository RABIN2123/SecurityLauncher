package com.rabin2123.data.local.applist

import com.rabin2123.data.local.applist.models.AppObjectData

interface AppListData {
    suspend fun getAppList(): List<AppObjectData>
}