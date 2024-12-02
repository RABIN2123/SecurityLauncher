package com.rabin2123.data.local.globalapplist

import com.rabin2123.data.local.globalapplist.models.AppObjectData

interface GlobalAppListData {
    /**
     * Get all apps without systems apps
     *
     * @return list with all apps
     */
    suspend fun getAppList(): List<AppObjectData>
}