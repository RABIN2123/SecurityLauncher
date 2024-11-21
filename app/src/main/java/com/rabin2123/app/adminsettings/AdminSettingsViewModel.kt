package com.rabin2123.app.adminsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForAdmin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminSettingsViewModel(private val repository: LocalRepositoryForAdmin): ViewModel() {
    private val _listApp = MutableStateFlow<List<AppObject>?>(emptyList())
    val listApp: StateFlow<List<AppObject>?> = _listApp

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            _listApp.update { repository.getAllAppList() }
        }
    }
}