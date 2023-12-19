package com.example.morello.ui.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class RegisterUiState(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val username: String,
    val isLoading: Boolean,
    val isRegisterButtonEnabled: Boolean,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val termsAndPolicyAgreed: Boolean = false,
    val error: String?,
) {
    companion object {
        val Empty = RegisterUiState(
            email = "",
            password = "",
            isLoading = false,
            isRegisterButtonEnabled = false,
            confirmPassword = "",
            username = "",
            error = null
        )
    }
}

class RegisterViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(RegisterUiState.Empty)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

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
        // TODO: Implement
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