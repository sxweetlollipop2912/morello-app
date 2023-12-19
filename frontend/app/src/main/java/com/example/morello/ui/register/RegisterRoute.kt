package com.example.morello.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun RegisterRoute(
    viewModel: RegisterViewModel,
    switchToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    RegisterScreen(
        uiState = uiState,
        onEmailChanged = viewModel::updateEmail,
        onNameChanged = viewModel::updateUsername,
        onPasswordChanged = viewModel::updatePassword,
        onConfirmPasswordChanged = viewModel::updateConfirmPassword,
        onRegisterButtonClicked = viewModel::onRegister,
        onShowPasswordChanged = viewModel::updatePasswordVisibility,
        onShowConfirmPasswordChanged = viewModel::updateConfirmPasswordVisibility,
        onAgreeTermsAndPolicyChanged = viewModel::updateTermsAndPolicyAgreeStatus,
        onLoginClicked = switchToLogin,
        modifier = modifier,
    )
}