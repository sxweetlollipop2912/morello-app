package com.example.morello.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordFormField(
    label: String,
    password: String,
    showPassword: Boolean,
    onPasswordChanged: (String) -> Unit,
    onShowPasswordChanged: (Boolean) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions(),
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        label = { Text(text = label) },
        shape = MaterialTheme.shapes.medium,
        value = password,
        onValueChange = onPasswordChanged,
        singleLine = true,
        keyboardActions = keyboardActions,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Password",
            )
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        suffix = {
            IconToggleButton(
                checked = showPassword,
                onCheckedChange = onShowPasswordChanged,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = if (showPassword) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    },
                    contentDescription = "Show password",
                )
            }
        },
    )
}
