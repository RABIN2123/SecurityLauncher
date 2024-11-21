package com.rabin2123.app.gridapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForAdmin
import com.rabin2123.domain.models.AppObject
import com.rabin2123.domain.repositoryinterfaces.LocalRepositoryForUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GridAppViewModel(private val repository: LocalRepositoryForUser): ViewModel() {

    private val _listApp = repository.getAllowedAppList()

}