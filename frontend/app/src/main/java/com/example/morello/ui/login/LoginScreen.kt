package com.example.morello.ui.theme.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginButtonClicked: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier,
) {
    val uiState by loginViewModel.uiState.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Login") }) }
    ) {
        Column(Modifier.padding(it)) {
            Text(text = "Login")
            Text(text = "Email")
            TextField(
                value = uiState.email,
                onValueChange = { email -> loginViewModel.updateEmail(email) })
            Text(text = "Password")
            TextField(
                value = uiState.email,
                onValueChange = { password -> loginViewModel.updatePassword(password) })
            Button(onClick = {
                loginViewModel.submitLogin()
            }) {
            }
        }
    }
}