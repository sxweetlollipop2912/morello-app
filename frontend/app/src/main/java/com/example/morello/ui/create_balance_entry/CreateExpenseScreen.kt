package com.example.morello.ui.create_balance_entry

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.Currency
import com.example.morello.data_layer.data_types.formattedWithSymbol
import com.example.morello.ui.components.CreateBalanceEntryTopBar
import com.example.morello.ui.components.FixedSignNumberEditField
import com.example.morello.ui.components.FormBackHandler
import com.example.morello.ui.components.StandaloneDatePickerDialogWithButton
import com.example.morello.ui.components.rememberFormBackHandlerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun Modifier.handleReopenKeyboard(focusManager: FocusManager, focusRequester: FocusRequester) =
    this.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (event.changes.any { it.pressed }) {
                    focusManager.clearFocus()
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateExpenseScreen(
    uiState: CreateExpenseUiState,
    onAmountChanged: (Currency) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeChanged: (OffsetDateTime) -> Unit,
    onCreate: () -> Unit,
    onConfirmGoBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var datePickerDisplayed by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val (amount, balanceAfter, name, description, dateTime, state, error) = uiState
    val formBackHandlerState = rememberFormBackHandlerState()
    val (first, second, third) = remember { FocusRequester.createRefs() }
    FormBackHandler(formBackHandlerState, onConfirmGoBack)
    BackHandler(onBack = {
        formBackHandlerState.goBack(!uiState.considerAsNew())

    })
    Scaffold(
        topBar = {
            CreateBalanceEntryTopBar(
                isLoading = state == State.Submitting,
                title = "New expense",
                onCreate = onCreate,
                onBack = {
                    formBackHandlerState.goBack(!uiState.considerAsNew())
                },
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            val titleTextStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            )
            if (error != null) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
            if (state == State.Submitting) {
                Text(
                    text = "Submitting...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
            FixedSignNumberEditField(
                value = amount,
                negativeSign = true,
                onValueChange = {
                    onAmountChanged(it)
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(first)
                    .focusProperties {
                        next = second
                    }
                    .handleReopenKeyboard(focusManager, first)
            )
            Text(
                text = "Balance after: ${balanceAfter?.formattedWithSymbol()}",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Name", style = titleTextStyle)
            OutlinedTextField(
                value = name.value,
                supportingText = {
                    if (name.error != null) {
                        Text(
                            text = name.error,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                },
                singleLine = true,
                isError = name.error != null,
                onValueChange = onNameChanged,
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .handleReopenKeyboard(focusManager, second)
                    .focusRequester(second)
                    .focusProperties { next = third }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Description", style = titleTextStyle)
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChanged,
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(third)
                    .handleReopenKeyboard(focusManager, third)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Date & Time", style = titleTextStyle)
                OutlinedButton(
                    onClick = { datePickerDisplayed = true },
                    shape = MaterialTheme.shapes.small,
                ) {
                    Row(
                        modifier = Modifier.padding(PaddingValues(vertical = 4.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker"
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = dateTime.format(dateFormatter))
                    }
                }
            }
            if (datePickerDisplayed) {
                StandaloneDatePickerDialogWithButton(
                    onDismissRequest = { datePickerDisplayed = false },
                    onDateTimeChanged = onDateTimeChanged,
                )
            }
        }
    }
}