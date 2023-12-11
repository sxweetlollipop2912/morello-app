package com.example.morello.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
    modifier: Modifier = Modifier,
) {
    TextField(
        label = { Text(text = label) },
        value = password,
        onValueChange = onPasswordChanged,
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        suffix = {
            IconButton(onClick = { onShowPasswordChanged(!showPassword) }) {
                Icon(
                    imageVector = if (showPassword) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = "Show password"
                )
            }
        }
    )
}
