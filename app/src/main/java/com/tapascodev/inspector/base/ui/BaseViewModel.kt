package com.tapascodev.inspector.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.base.domain.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel (
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _logout = MutableStateFlow(false)
    val logout: StateFlow<Boolean> = _logout

    suspend fun logout() = viewModelScope.launch {
        logoutUseCase {
            _logout.value = it
        }
    }
}