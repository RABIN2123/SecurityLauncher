package com.rabin2123.app.adminsettings

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.app.adminsettings.models.AppObjectWithCheckBox
import com.rabin2123.app.services.filechecker.FileSystemObserverService
import com.rabin2123.app.utils.AdminUtils
import com.rabin2123.app.utils.SettingsEvent
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.models.SettingsObject
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForAdmin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LauncherSettingsViewModel(private val repository: LocalRepositoryForAdmin) : ViewModel(),
    KoinComponent {
    private val adminUtils: AdminUtils by inject()
    private val _allListApp = MutableStateFlow<List<AppObject>>(emptyList())
    private val _allowedAppList = repository
        .getAllowedAppList()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val listApp = combine(_allListApp, _allowedAppList) { allList, allowedList ->
        allList.map { data ->
            AppObjectWithCheckBox(
                name = data.name,
                packageName = data.packageName,
                isChecked = allowedList.contains(data)
            )
        }
    }

    private val _settingsList = MutableStateFlow<SettingsObject?>(null)
    val settingList: StateFlow<SettingsObject?> = _settingsList

    init {
        initData()
    }

    private fun initData() {

        viewModelScope.launch(Dispatchers.IO) {
            _allListApp.update { repository.getAllAppList() }
            _settingsList.update { repository.getSettingsList() }
        }
    }

    fun saveLauncherSettings(
        settings: SettingsObject,
        allowedAppList: List<AppObjectWithCheckBox>,
        applicationContext: Context
    ) {
        setSettings(settings, applicationContext)
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSettingsList(settings)
            repository.deleteAllAllowedAppList()

            repository.setAllowedAppList(allowedAppList.map { data ->
                AppObject(
                    name = data.name,
                    packageName = data.packageName
                )
            })
        }
    }

    private fun setSettings(
        newSettings: SettingsObject,
        applicationContext: Context
    ) {
        _settingsList.value?.let { oldSettings ->
            if (newSettings.sendToMlBazaar != oldSettings.sendToMlBazaar) {
                applicationContext.sendBroadcast(
                    Intent(
                        if (newSettings.sendToMlBazaar) FileSystemObserverService.Actions.START.toString()
                        else FileSystemObserverService.Actions.STOP.toString()
                    )
                        .setClassName(
                            applicationContext.packageName,
                            "com.rabin2123.app.services.filechecker.StartupReceiverFileSystem"
                        )
                )
            }
            if (newSettings.blockSettings != oldSettings.blockSettings) {
                adminUtils.onEvent(
                    SettingsEvent.BlockApps(
                        arrayOf("com.android.settings"),
                        newSettings.blockSettings
                    )
                )
            } else {
                oldSettings.blockSettings
            }
            if (newSettings.blockGps != oldSettings.blockGps) {
                adminUtils.onEvent(SettingsEvent.BlockGps(newSettings.blockGps))
            }
            if (newSettings.blockUsb != oldSettings.blockUsb) {
                adminUtils.onEvent(SettingsEvent.BlockUsb(newSettings.blockUsb))
            }
            if (newSettings.blockCamera != oldSettings.blockCamera) {
                adminUtils.onEvent(SettingsEvent.BlockCamera(newSettings.blockCamera))
            }
        }
    }
}