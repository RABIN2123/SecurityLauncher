package com.example.data.applist

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.example.data.applist.models.AppObject


class AppListRepositoryImpl : AppListRepository {
    override suspend fun getAppList(context: Context): List<AppObject> {
        val appList = mutableListOf<AppObject>()
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        val resolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(
                intent,
                PackageManager.ResolveInfoFlags.of(0L)
            )
        } else {
            pm.queryIntentActivities(intent, 0)
        }
        resolveInfos.forEach { resolveInfo ->
            appList.add(
                AppObject(
                    name = resolveInfo.activityInfo.applicationInfo.loadLabel(pm).toString(),
                    packageName = resolveInfo.activityInfo.packageName.toString(),
                    icon = resolveInfo.activityInfo.loadIcon(pm)
                )
            )
        }
        return appList;
    }
}