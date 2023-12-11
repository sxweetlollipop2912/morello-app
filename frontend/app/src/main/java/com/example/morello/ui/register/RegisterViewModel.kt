package com.example.morello.ui.register


data class RegisterUiState(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val name: String,
    val isLoading: Boolean,
    val isRegisterButtonEnabled: Boolean,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val error: String?,
) {
    companion object {
        val Empty = RegisterUiState(
            email = "",
            password = "",
            isLoading = false,
            isRegisterButtonEnabled = false,
            confirmPassword = "",
            name = "",
            error = null
        )
    }
}