package com.rabin2123.app.adminsettings

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.app.adminsettings.models.AppObjectWithCheckBox
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForAdmin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LauncherSettingsViewModel(private val repository: LocalRepositoryForAdmin) : ViewModel() {
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

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            _allListApp.update { repository.getAllAppList() }
        }
    }

    fun saveLauncherSettings(allowedAppList: List<AppObjectWithCheckBox>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllAllowedAppList()
            Thread.sleep(1000)
            repository.setAllowedAppList(allowedAppList.map { data ->
                AppObject(
                    name = data.name,
                    packageName = data.packageName
                )
            })
        }

    }
}