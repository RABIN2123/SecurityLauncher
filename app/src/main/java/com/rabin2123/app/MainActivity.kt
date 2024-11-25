package com.rabin2123.app

import android.Manifest.permission
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.rabin2123.app.gridapp.GridAppFragment
import com.rabin2123.app.services.filechecker.StartupReceiverFileSystem
import com.rabin2123.app.utils.KioskUtil
import org.koin.android.ext.android.get


class MainActivity : AppCompatActivity() {

    private val kioskUtil: KioskUtil = get()


    private val startupReceiverFileSystem by lazy {
        StartupReceiverFileSystem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerReceiver(
            startupReceiverFileSystem,
            IntentFilter("START_FILE_OBSERVER_SERVICE"),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                RECEIVER_EXPORTED
            } else {
                0
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(
                    permission.POST_NOTIFICATIONS,
                    permission.RECEIVE_BOOT_COMPLETED,
                    permission.MANAGE_EXTERNAL_STORAGE,
                    permission.INTERNET
                ), 1
            )
        }
        if (kioskUtil.getStateAdminActive()) {
            sendBroadcast(
                Intent("START_FILE_OBSERVER_SERVICE").setClassName(
                    packageName,
                    "com.rabin2123.app.services.filechecker.StartupReceiverFileSystem"
                )
            )
            supportFragmentManager.commit {
                replace<GridAppFragment>(R.id.activity_home)
                setReorderingAllowed(true)
            }
        } else {
            kioskUtil.setAdminPermission()
        }

    }

    override fun onDestroy() {
        unregisterReceiver(startupReceiverFileSystem)
        super.onDestroy()
    }

}