package com.example.morello.ui.theme.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val isLoginButtonEnabled: Boolean,
    val error: String?,
) {
    companion object {
        val Empty = LoginUiState(
            email = "",
            password = "",
            isLoading = false,
            isLoginButtonEnabled = false,
            error = null
        )
    }
}

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState.Empty)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private fun shouldEnableLoginButton(): Boolean {
        return _uiState.value.email.isNotEmpty() && _uiState.value.password.isNotEmpty()
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        if (shouldEnableLoginButton()) {
            _uiState.value = _uiState.value.copy(isLoginButtonEnabled = true)
        } else {
            _uiState.value = _uiState.value.copy(isLoginButtonEnabled = false)
        }
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        if (shouldEnableLoginButton()) {
            _uiState.value = _uiState.value.copy(isLoginButtonEnabled = true)
        } else {
            _uiState.value = _uiState.value.copy(isLoginButtonEnabled = false)
        }
    }

    fun submitLogin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                userRepository.login(_uiState.value.email, _uiState.value.password)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}