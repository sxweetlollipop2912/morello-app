package com.example.morello.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection.Ltr

@Composable
fun FixedSignNumberEditField(
    value: UInt,
    negativeSign: Boolean,
    onValueChange: (UInt) -> Unit,
    modifier: Modifier = Modifier,
) {
    val direction = LocalLayoutDirection.current
    val selection =
        if (direction == Ltr) {
            TextRange(value.toString().length + if (negativeSign) 1 else 0)
        } else
            TextRange.Zero
    val valueString = if (negativeSign) {
        "-${value}"
    } else {
        value.toString()
    }
    val textFieldValue = TextFieldValue(text = valueString, selection = selection)
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newTfv: TextFieldValue ->
            if (negativeSign) {
                if (newTfv.text.startsWith("-")) {
                    onValueChange(newTfv.text.substring(1).toUIntOrNull() ?: 0U)
                } else {
                    onValueChange(newTfv.text.toUIntOrNull() ?: 0U)
                }
            } else {
                onValueChange(newTfv.text.toUIntOrNull() ?: 0U)
            }
        },
        prefix = {
            Button(onClick = {}, enabled = false) {
                Text(text = "VND")
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.error,
        ),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
//                    tfv = tfv.copy(selection = TextRange(0, tfv.text.length))
                }
            },
    )
}
