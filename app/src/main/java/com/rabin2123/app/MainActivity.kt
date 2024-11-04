package com.rabin2123.app

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.rabin2123.app.gridapp.GridAppFragment
import com.rabin2123.app.services.AdminReceiver

class MainActivity : AppCompatActivity() {

    private val devicePolicyManager: DevicePolicyManager by lazy {
        this@MainActivity.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    }
    private val myDeviceAdmin: ComponentName by lazy {
        ComponentName(this@MainActivity, AdminReceiver::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (devicePolicyManager.isAdminActive(myDeviceAdmin)) {
            supportFragmentManager.commit {
                replace<GridAppFragment>(R.id.activity_home)
                setReorderingAllowed(true)
            }
        } else {
            activateAdmin()
        }
    }

    private fun activateAdmin() {
            this@MainActivity.startActivity(
                Intent().setComponent(
                    ComponentName(
                        "com.android.settings", "com.android.settings.DeviceAdminSettings"
                    )
                )
            )
        if (devicePolicyManager.isDeviceOwnerApp(this@MainActivity.packageName)){
            val filter = IntentFilter(Intent.ACTION_MAIN)
            filter.addCategory(Intent.CATEGORY_HOME)
            filter.addCategory(Intent.CATEGORY_DEFAULT)
            val activity = ComponentName(this@MainActivity, MainActivity::class.java)
            devicePolicyManager.addPersistentPreferredActivity(myDeviceAdmin, filter, activity)
        }
    }
}