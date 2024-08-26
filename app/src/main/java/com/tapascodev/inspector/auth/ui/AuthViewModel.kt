package com.tapascodev.inspector.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.auth.domain.CreateUserUseCase
import com.tapascodev.inspector.auth.domain.CurrentUserUseCase
import com.tapascodev.inspector.auth.domain.LoginUseCase
import com.tapascodev.inspector.auth.domain.RegisterUseCase
import com.tapascodev.inspector.auth.domain.model.UserSignIn
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val currentUserUseCase: CurrentUserUseCase
) : ViewModel(){

    private val _loginState = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val loginState: StateFlow<Resource<Boolean>> = _loginState

    private val _registerState = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val registerState: StateFlow<Resource<Boolean>> = _registerState

    private val _currentState = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val currentState: StateFlow<Resource<Boolean>> = _currentState

    fun login(email : String,password : String){

        _loginState.value = Resource.Loading

        if(email.isEmpty() || password.isEmpty()){
            _loginState.value = Resource.Failure(false, 0, "Email or Password can´t empty")
            return
        }

        viewModelScope.launch {
            _loginState.value = loginUseCase.invoke(email, password)
        }
    }

    fun register (userSignIn: UserSignIn) {
        _registerState.value = Resource.Loading
        viewModelScope.launch {
            _registerState.value = registerUseCase.invoke(userSignIn)
        }
    }

    fun createUser(userSignIn: UserSignIn) {
        viewModelScope.launch {
            createUserUseCase.invoke(userSignIn)
        }
    }

    fun currentUser() {
        _currentState.value = Resource.Loading
        viewModelScope.launch { _currentState.value = currentUserUseCase.invoke() }
    }
}