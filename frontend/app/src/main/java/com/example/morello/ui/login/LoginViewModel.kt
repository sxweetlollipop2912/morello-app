package com.example.morello.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

enum class LoginState {
    Input,
    Loading,
    Success,
    Error,
}

data class LoginUiState(
    val email: String,
    val password: String,
    val isLoginButtonEnabled: Boolean,
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
    val loginState: LoginState = LoginState.Input,
    val error: String?,
) {
    companion object {
        val Empty = LoginUiState(
            email = "",
            password = "",
            isLoginButtonEnabled = false,
            rememberMe = false,
            error = null
        )
    }
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
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

    fun updatePasswordVisibility(showPassword: Boolean) {
        _uiState.value = _uiState.value.copy(showPassword = showPassword)
    }

    fun updateRememberMeStatus(rememberMe: Boolean) {
        _uiState.value = _uiState.value.copy(rememberMe = rememberMe)
    }

    fun submitLogin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loginState = LoginState.Loading)
            try {
                userRepository.login(_uiState.value.email, _uiState.value.password)
                _uiState.value = _uiState.value.copy(
                    loginState = LoginState.Success,
                    error = null
                )
            } catch (e: SocketTimeoutException) {
                _uiState.value =
                    _uiState.value.copy(loginState = LoginState.Error, error = "Connection timeout")
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(loginState = LoginState.Error, error = e.message)

            }
        }
    }
}