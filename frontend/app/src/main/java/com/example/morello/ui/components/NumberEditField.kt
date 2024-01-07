package com.example.morello.ui.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection.Ltr
import com.example.morello.data_layer.data_types.Currency
import com.example.morello.data_layer.data_types.formattedNoSymbol
import com.example.morello.data_layer.data_types.formattedStrToCurrency

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FixedSignNumberEditField(
    value: Currency,
    negativeSign: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge.copy(
        textAlign = TextAlign.End,
        color = MaterialTheme.colorScheme.error,
    ),
    onValueChange: (Currency) -> Unit,
    prefix: @Composable () -> Unit = {},
    suffix: @Composable () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.medium,
    autoFocus: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val direction = LocalLayoutDirection.current
    val selection =
        if (direction == Ltr) {
            TextRange(value.formattedNoSymbol().length + if (negativeSign) 1 else 0)
        } else
            TextRange.Zero
    val valueString = if (negativeSign) {
        "-${value.formattedNoSymbol()}"
    } else {
        value.formattedNoSymbol()
    }
    val textFieldValue = TextFieldValue(text = valueString, selection = selection)
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newTfv: TextFieldValue ->
            if (negativeSign && newTfv.text.startsWith("-")) {
                onValueChange(formattedStrToCurrency(newTfv.text.substring(1)))
            } else {
                onValueChange(formattedStrToCurrency(newTfv.text))
                Log.d("FixedSignNumberEditField", "newTfv.text: ${newTfv.text}")
            }
        },
        shape = shape,
        prefix = prefix,
        suffix = suffix,
        singleLine = true,
        textStyle = textStyle.copy(
            textAlign = TextAlign.End,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = if (autoFocus) {
            modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        keyboardController?.show()
//                    tfv = tfv.copy(selection = TextRange(0, tfv.text.length))
                    }
                }
        } else {
            modifier
        },
    )
}
