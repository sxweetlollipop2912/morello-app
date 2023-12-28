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
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.data_layer.data_sources.data_types.formatted
import com.example.morello.data_layer.data_sources.data_types.formattedStrToCurrency

@Composable
fun FixedSignNumberEditField(
    value: Currency,
    negativeSign: Boolean,
    onValueChange: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    val direction = LocalLayoutDirection.current
    val selection =
        if (direction == Ltr) {
            TextRange(value.formatted().length + if (negativeSign) 1 else 0)
        } else
            TextRange.Zero
    val valueString = if (negativeSign) {
        "-${value.formatted()}"
    } else {
        value.formatted()
    }
    val textFieldValue = TextFieldValue(text = valueString, selection = selection)
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newTfv: TextFieldValue ->
            if (negativeSign && newTfv.text.startsWith("-")) {
                onValueChange(formattedStrToCurrency(newTfv.text.substring(1)))
            } else {
                onValueChange(formattedStrToCurrency(newTfv.text))
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
