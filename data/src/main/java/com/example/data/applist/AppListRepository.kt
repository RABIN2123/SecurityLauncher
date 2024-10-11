package com.example.data.applist

import android.content.Context
import com.example.data.applist.models.AppObject

interface AppListRepository {
    suspend fun getAppList(context: Context): List<AppObject>
}