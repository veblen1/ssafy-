package com.example.project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BiometricViewModel : ViewModel() {

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    val authenticationState: LiveData<AuthenticationState> get() = _authenticationState

    // 인증 상태
    fun setAuthenticationState(state: AuthenticationState) {
        _authenticationState.value = state
    }
}

sealed class AuthenticationState {
    object SUCCESS : AuthenticationState()
    object FAILURE : AuthenticationState()
    data class ERROR(val message: String) : AuthenticationState()
}
