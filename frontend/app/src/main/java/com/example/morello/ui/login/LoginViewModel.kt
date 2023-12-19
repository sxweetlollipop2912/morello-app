package com.example.morello.ui.theme.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.morello.data_layer.data_sources.RemoteUserDataSource
import com.example.morello.data_layer.data_sources.SettingDataSource
import com.example.morello.data_layer.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class LoginRequestStatus {
    SUCCESS,
    ERROR,
    LOADING,
}

data class LoginUiState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val loggedIn: Boolean = false,
    val isLoginButtonEnabled: Boolean,
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
    val requestStatus: LoginRequestStatus? = null,
    val error: String?,
) {
    companion object {
        val Empty = LoginUiState(
            email = "",
            password = "",
            isLoading = false,
            isLoginButtonEnabled = false,
            rememberMe = false,
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

    fun updatePasswordVisibility(showPassword: Boolean) {
        _uiState.value = _uiState.value.copy(showPassword = showPassword)
    }

    fun updateRememberMeStatus(rememberMe: Boolean) {
        _uiState.value = _uiState.value.copy(rememberMe = rememberMe)
    }

    fun submitLogin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(requestStatus = LoginRequestStatus.LOADING)
            try {
                userRepository.login(_uiState.value.email, _uiState.value.password)
                Log.d("LoginViewModel", "Logged in")
                _uiState.value = _uiState.value.copy(
                    requestStatus = LoginRequestStatus.SUCCESS,
                )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(error = e.message, requestStatus = LoginRequestStatus.ERROR)
            }
        }
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                LoginViewModel(
                    userRepository = UserRepository(
                        remoteUserDataSource = RemoteUserDataSource.createMockedInstance(),
                        settingDataSource = SettingDataSource(this[APPLICATION_KEY]!!)
                    )
                )
            }
        }
    }

}