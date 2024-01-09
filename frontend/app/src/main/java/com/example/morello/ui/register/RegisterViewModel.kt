package com.example.morello.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class RegisterState {
    Input,
    Loading,
    Success,
}

data class RegisterUiState(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val username: String,
    val isRegisterButtonEnabled: Boolean,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val termsAndPolicyAgreed: Boolean = false,
    val registerState: RegisterState = RegisterState.Input,
    val error: String?,
) {
    companion object {
        val empty = RegisterUiState(
            email = "",
            password = "",
            isRegisterButtonEnabled = false,
            confirmPassword = "",
            username = "",
            error = null
        )
    }
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(RegisterUiState.empty)
    val uiState: StateFlow<RegisterUiState> = _uiState.transform {
        emit(
            it.copy(
                isRegisterButtonEnabled = shouldEnableRegisterButton(),
                error = if (it.email.isNotEmpty() && !isValidEmail(it.email)) {
                    "Invalid email"
                } else if (it.password.isNotEmpty() && !isValidPassword(it.password)) {
                    "Password must be at least 8 characters"
                } else if (it.confirmPassword.isNotEmpty() && it.confirmPassword != it.password) {
                    "Passwords do not match"
                } else {
                    null
                }
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = RegisterUiState.empty,
    )

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun shouldEnableRegisterButton(): Boolean {
        return _uiState.value.email.isNotEmpty()
                && _uiState.value.password.isNotEmpty()
                && _uiState.value.confirmPassword.isNotEmpty()
                && _uiState.value.username.isNotEmpty()
                && _uiState.value.termsAndPolicyAgreed
                && _uiState.value.password == _uiState.value.confirmPassword
                && isValidEmail(_uiState.value.email)
                && isValidPassword(_uiState.value.password)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePasswordVisibility(showPassword: Boolean) {
        _uiState.value = _uiState.value.copy(showPassword = showPassword)
    }

    fun updateConfirmPasswordVisibility(showConfirmPassword: Boolean) {
        _uiState.value = _uiState.value.copy(showConfirmPassword = showConfirmPassword)
    }

    fun onRegister() {
        if (!shouldEnableRegisterButton()) {
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(registerState = RegisterState.Loading)
            try {
                userRepository.register(
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    username = _uiState.value.username,
                )
                _uiState.value = _uiState.value.copy(
                    registerState = RegisterState.Success,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(registerState = RegisterState.Input, error = e.message)
            }
        }
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updateTermsAndPolicyAgreeStatus(termsAndPolicyAgreeStatus: Boolean) {
        _uiState.value = _uiState.value.copy(termsAndPolicyAgreed = termsAndPolicyAgreeStatus)
    }
}