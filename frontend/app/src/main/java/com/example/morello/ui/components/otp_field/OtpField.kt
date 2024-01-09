package com.example.morello.ui.components.otp_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpField(
    onTextChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val (item1, item2, item3, item4) = FocusRequester.createRefs()
    var text by remember {
        mutableStateOf("    ")
    }
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        OtpChar(
            onDoneEditing = {
                text = text
                    .replaceRange(0, 1, it)
                onTextChange(text)
            },
            modifier = Modifier
                .focusRequester(item1)
                .focusProperties {
                    next = item2
                    previous = item1
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        OtpChar(
            onDoneEditing = {
                text = text
                    .replaceRange(1, 2, it)
                onTextChange(text)
            },
            modifier = Modifier
                .focusRequester(item2)
                .focusProperties {
                    next = item3
                    previous = item1
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        OtpChar(
            onDoneEditing = {
                text = text
                    .replaceRange(2, 3, it)
                onTextChange(text)
            },
            modifier = Modifier
                .focusRequester(item3)
                .focusProperties {
                    next = item4
                    previous = item2
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        OtpChar(
            onDoneEditing = {
                val newText = text
                    .replaceRange(3, 4, it)
                onTextChange(newText)
            },
            modifier = Modifier
                .focusRequester(item4)
                .focusProperties {
                    previous = item3
                    next = item4
                }
        )
    }
}
