package com.example.morello.ui.components.otp_field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpChar(
    onDoneEditing: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val pattern = remember { Regex("^[^\\t]*\$") } //to not accept the tab key as value
    val (text, setText) = remember { mutableStateOf("") }
    val maxChar = 1
    val focusManager = LocalFocusManager.current

    LaunchedEffect(
        key1 = text,
    ) {
        if (text.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= maxChar &&
                    (it.isEmpty() || it.matches(pattern))
                ) {
                    setText(it)
                }
                if (it.length == maxChar) {
                    onDoneEditing(it)
                }
            },
            modifier = modifier
                .width(50.dp)
                .onKeyEvent {
                    if (it.key == Key.Tab) {
                        focusManager.moveFocus(FocusDirection.Next)
                        true
                    }
                    if (text.isEmpty() && it.key == Key.Backspace) {
                        focusManager.moveFocus(FocusDirection.Previous)
                    }
                    false
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )
        Divider(
            modifier = Modifier
                .width(28.dp)
                .padding(bottom = 2.dp)
                .offset(y = (-10).dp),
            thickness = 1.dp
        )
    }
}
