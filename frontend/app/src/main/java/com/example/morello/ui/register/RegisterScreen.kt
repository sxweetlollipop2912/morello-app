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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.morello.ui.components.MorelloTopBar
import com.example.morello.ui.components.PasswordFormField
import com.example.morello.ui.components.SectionDividerWithText

@OptIn(ExperimentalMaterial3Api::class)
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
    onSwitchToLoginClicked: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                        text = "Joint us before?",
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
                        onRegisterButtonClicked()
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
            Text(text = "Sign in", style = MaterialTheme.typography.headlineLarge)
            Text(
                text = "Hello there! Let's create your account.",
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedTextField(
                label = { Text(text = "Email") },
                shape = MaterialTheme.shapes.medium,
                value = uiState.name,
                onValueChange = onNameChanged,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.padding(spacing))
            OutlinedTextField(
                label = { Text(text = "Username") },
                shape = MaterialTheme.shapes.medium,
                value = uiState.name,
                onValueChange = onNameChanged,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.padding(spacing))
            PasswordFormField(
                label = "Password",
                password = uiState.password,
                showPassword = uiState.showPassword,
                onPasswordChanged = onPasswordChanged,
                onShowPasswordChanged = onShowPasswordChanged,
            )
            Spacer(modifier = Modifier.padding(spacing))
            PasswordFormField(
                label = "Confirm Password",
                password = uiState.confirmPassword,
                showPassword = uiState.showConfirmPassword,
                onPasswordChanged = onConfirmPasswordChanged,
                onShowPasswordChanged = onShowConfirmPasswordChanged,
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
            Spacer(modifier = Modifier.padding(spacing))
            Button(
                onClick = onRegisterButtonClicked,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create account")
                if (uiState.isLoading) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
            SectionDividerWithText(text = "Or")
            OutlinedButton(
                shape = MaterialTheme.shapes.small,
                onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login with Google")
            }
            OutlinedButton(
                shape = MaterialTheme.shapes.small,
                onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login with Facebook")
            }
        }
    }
}