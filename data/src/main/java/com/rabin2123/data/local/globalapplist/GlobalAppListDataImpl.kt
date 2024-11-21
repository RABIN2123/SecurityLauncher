package com.rabin2123.data.local.globalapplist

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import com.rabin2123.data.local.globalapplist.models.AppObjectData


class GlobalAppListDataImpl(private val context: Context) : GlobalAppListData {
    override suspend fun getAppList(): List<AppObjectData> {
        val appList = mutableListOf<AppObjectData>()
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
            if (resolveInfo.activityInfo.packageName.toString() != context.packageName)
                appList.add(
                    AppObjectData(
                        name = resolveInfo.activityInfo.applicationInfo.loadLabel(pm).toString(),
                        packageName = resolveInfo.activityInfo.packageName.toString(),
//                        icon = resolveInfo.activityInfo.loadIcon(pm)
                    )
                )
        }
        return appList;
    }
}