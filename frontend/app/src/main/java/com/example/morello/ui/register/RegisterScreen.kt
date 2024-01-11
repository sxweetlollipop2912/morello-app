package com.example.morello.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onEmailChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onRegisterButtonClicked: () -> Unit,
    onShowPasswordChanged: (Boolean) -> Unit,
    onShowConfirmPasswordChanged: (Boolean) -> Unit,
    onAgreeTermsAndPolicyChanged: (Boolean) -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.registerState == RegisterState.Success) {
        onRegisterSuccess()
    }
    val (first, second, third, forth) = remember { FocusRequester.createRefs() }
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
                        text = "Joined us before?",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    ClickableText(
                        text = AnnotatedString("Log in"),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        onLoginClicked()
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
            val spacing = 12.dp
            Text(text = "Sign up", style = MaterialTheme.typography.headlineLarge)
            Text(
                text = "Hello there! Let's create your account.",
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedTextField(
                label = { Text(text = "Email") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                value = uiState.email,
                onValueChange = onEmailChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "Email",
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(first)
                    .focusProperties {
                        next = second
                    }
            )
            Spacer(modifier = Modifier.padding(spacing))
            OutlinedTextField(
                label = { Text(text = "Username") },
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                value = uiState.username,
                onValueChange = onNameChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Username",
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(second)
                    .focusProperties {
                        next = third
                    },
            )
            Spacer(modifier = Modifier.padding(spacing))
            PasswordFormField(
                label = "Password",
                password = uiState.password,
                showPassword = uiState.showPassword,
                onPasswordChanged = onPasswordChanged,
                onShowPasswordChanged = onShowPasswordChanged,
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(third)
                    .focusProperties {
                        next = forth
                    },
            )
            Spacer(modifier = Modifier.padding(spacing))
            PasswordFormField(
                label = "Confirm Password",
                password = uiState.confirmPassword,
                showPassword = uiState.showConfirmPassword,
                onPasswordChanged = onConfirmPasswordChanged,
                onShowPasswordChanged = onShowConfirmPasswordChanged,
                keyboardActions = KeyboardActions(
                    onAny = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(forth)
            )
            Spacer(modifier = Modifier.padding(spacing))
            Row {
                val textStyle = MaterialTheme.typography.bodySmall
                val highlightedTextStyle = textStyle.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                )
                Checkbox(
                    checked = uiState.termsAndPolicyAgreed,
                    onCheckedChange = onAgreeTermsAndPolicyChanged
                )
                Column(horizontalAlignment = Alignment.End) {
                    Row {
                        Text(text = "I agree to Platform ", style = textStyle)
                    }
                    Row {
                        ClickableText(
                            text = AnnotatedString("Terms of Service"),
                            style = highlightedTextStyle
                        ) {
                        }
                        Text(text = " and ", style = textStyle)
                        ClickableText(
                            text = AnnotatedString("Privacy Policy"),
                            style = highlightedTextStyle
                        ) {
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(spacing / 2))
            if (!uiState.error.isNullOrEmpty()) {
                Text(
                    text = uiState.error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Button(
                onClick = onRegisterButtonClicked,
                enabled = uiState.isRegisterButtonEnabled,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Create account",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                if (uiState.registerState == RegisterState.Loading) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.padding(0.dp).size(32.dp),
                    )
                }
            }
            SectionDividerWithText(text = "Or")
            OutlinedButton(
                shape = MaterialTheme.shapes.small,
                onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Register with Google")
            }
            OutlinedButton(
                shape = MaterialTheme.shapes.small,
                onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Register with Facebook")
            }
        }
    }
}