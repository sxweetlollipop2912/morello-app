package com.example.morello.ui.create_balance_entry

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.data_layer.data_sources.data_types.formatted
import com.example.morello.ui.components.CreateBalanceEntryTopBar
import com.example.morello.ui.components.FixedSignNumberEditField
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExpenseScreen(
    uiState: CreateExpenseUiState,
    onAmountChanged: (Currency) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeChanged: (LocalDateTime) -> Unit,
    onCreate: () -> Unit,
    onBack: () -> Unit,
    onConfirmGoBack: () -> Unit,
    onCancelGoBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var datePickerDisplayed by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val (amount, balanceAfter, name, description, dateTime, state, error) = uiState
    BackHandler(onBack = onBack)
    if (state == State.TryToGoBack) {
        AlertDialog(
            onDismissRequest = { onCancelGoBack() },
            title = { Text(text = "Discard changes?") },
            text = { Text(text = "Are you sure you want to discard changes?") },
            confirmButton = {
                Button(onClick = { onConfirmGoBack() }) {
                    Text(text = "Discard")
                }
            },
            dismissButton = {
                Button(onClick = { onCancelGoBack() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
    Scaffold(
        topBar = {
            CreateBalanceEntryTopBar(
                isLoading = state == State.Submitting,
                title = "New expense",
                onCreate = onCreate,
                onBack = onBack,
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
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Balance after: ${balanceAfter.formatted()} VND",
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
                isError = name.error != null,
                onValueChange = onNameChanged,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Description", style = titleTextStyle)
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChanged,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
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
                DatePickerDialog(
                    onDismissRequest = { datePickerDisplayed = false },
                    confirmButton = {
                        Button(onClick = {
                            if (datePickerState.selectedDateMillis == null) {
                                onDateTimeChanged(
                                    LocalDateTime.now()
                                )
                            } else {
                                onDateTimeChanged(
                                    LocalDateTime.ofInstant(
                                        Instant.ofEpochMilli(datePickerState.selectedDateMillis!!),
                                        ZoneId.systemDefault(),
                                    )
                                )
                            }
                            datePickerDisplayed = false
                        }) {
                            Text(text = "Submit")
                        }
                    }) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}