package com.tapascodev.inspector.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.base.ui.BaseViewModel
import com.tapascodev.inspector.home.domain.LogoutUseCase
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _stateLogout = MutableStateFlow<Resource<Unit>?>(null)
    val stateLogout: StateFlow<Resource<Unit>?> = _stateLogout.asStateFlow()


    fun logout() {
        viewModelScope.launch {
            _stateLogout.value = Resource.Loading
            _stateLogout.value = logoutUseCase.invoke()
        }
    }


}