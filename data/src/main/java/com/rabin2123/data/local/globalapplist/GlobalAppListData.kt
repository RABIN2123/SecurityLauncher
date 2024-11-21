package com.rabin2123.data.local.globalapplist

import com.rabin2123.data.local.globalapplist.models.AppObjectData

interface GlobalAppListData {
    suspend fun getAppList(): List<AppObjectData>
}