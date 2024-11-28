package com.rabin2123.app.gridapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GridAppViewModel(private val repository: LocalRepositoryForUser) : ViewModel() {

    private val _listApp: StateFlow<List<AppObject>> = repository
        .getAllowedAppList()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val listApp = _listApp
    private val _resultCheckPassword: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val resultCheckPassword: StateFlow<Boolean> = _resultCheckPassword

    fun checkPassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _resultCheckPassword.update {
                repository.checkAdminPassword(
                    password
                )
            }
        }
    }
}