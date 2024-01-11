package com.example.morello.ui.theme.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.morello.ui.components.MorelloTopBar
import com.example.morello.ui.components.PasswordFormField
import com.example.morello.ui.components.SectionDividerWithText
import com.example.morello.ui.login.LoginState
import com.example.morello.ui.login.LoginUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginButtonClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onRememberMeChanged: (Boolean) -> Unit,
    onShowPasswordChanged: (Boolean) -> Unit,
    onGoogleLoginClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier,
) {
    if (uiState.loginState == LoginState.Success) {
        onLoginSuccess()
    }
    val (first, second) = remember { FocusRequester.createRefs() }
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            MorelloTopBar(head = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Logo",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Morello",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }, tail = {
                Row {
                    Text(
                        text = "First time here?",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    ClickableText(
                        text = AnnotatedString("Sign up"),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        onSignInClicked()
                    }
                }
            })
        },
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            val spacing = 16.dp
            Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
            Text(
                text = "Welcome back! Please enter your details.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.padding(spacing))
            OutlinedTextField(
                label = { Text(text = "Email") },
                shape = MaterialTheme.shapes.medium,
                value = uiState.email,
                singleLine = true,
                onValueChange = onEmailChanged,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email",
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(first)
                    .focusProperties {
                        next = second
                    }
            )
            Spacer(modifier = Modifier.padding(spacing))
            PasswordFormField(
                label = "Password",
                password = uiState.password,
                showPassword = uiState.showPassword,
                onPasswordChanged = onPasswordChanged,
                onShowPasswordChanged = onShowPasswordChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(second)
            )
            Spacer(modifier = Modifier.padding(spacing))
            if (uiState.loginState == LoginState.Error) {
                Text(
                    text = uiState.error ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(
                onClick = onLoginButtonClicked,
                enabled = uiState.isLoginButtonEnabled,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                if (uiState.loginState == LoginState.Loading) {
                    Spacer(modifier = Modifier.width(4.dp))
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(0.dp).size(32.dp),
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Absolute.Right,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = uiState.rememberMe, onCheckedChange = onRememberMeChanged)
                Text(text = "Remember me")
            }
            ClickableText(
                text = AnnotatedString("Forgot password?"),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                onForgotPasswordClicked()
            }
            Spacer(modifier = Modifier.padding(spacing))
            SectionDividerWithText("Or")
            Spacer(modifier = Modifier.padding(spacing))
            OutlinedButton(
                shape = MaterialTheme.shapes.small,
                onClick = onGoogleLoginClicked, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login with Google")
            }
            OutlinedButton(
                shape = MaterialTheme.shapes.small,
                onClick = onGoogleLoginClicked, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login with Facebook")
            }
        }
    }
}