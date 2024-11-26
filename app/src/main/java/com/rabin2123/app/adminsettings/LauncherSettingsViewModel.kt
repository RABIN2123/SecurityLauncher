package com.rabin2123.app.adminsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.app.adminsettings.models.AppObjectWithCheckBox
import com.rabin2123.app.utils.AdminUtils
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
        allowedAppList: List<AppObjectWithCheckBox>
    ) {
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
        setSettings(settings)
    }

    private fun setSettings(settings: SettingsObject) {
        adminUtils.blockApps(arrayOf("com.android.settings"), settings.blockSettings)
        adminUtils.blockGps(settings.blockGps)
        adminUtils.blockUsb(settings.blockUsb)
        adminUtils.blockCamera(settings.blockCamera)
    }
}