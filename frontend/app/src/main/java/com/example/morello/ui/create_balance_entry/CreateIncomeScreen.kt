package com.example.morello.ui.create_balance_entry

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.ui.components.CreateBalanceEntryTopBar
import com.example.morello.ui.components.FixedSignNumberEditField
import com.example.morello.ui.components.SectionDividerWithText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIncomeScreen(
    amount: Currency,
    balanceAfter: Currency,
    name: String,
    description: String,
    dateTime: LocalDateTime,
    onBalanceChanged: (Currency) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeChanged: (LocalDateTime) -> Unit,
    onCreate: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var datePickerDisplayed by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var isAddingSession by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CreateBalanceEntryTopBar(
                title = "New income",
                onCreate = onCreate,
                onBack = onBack,
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
            FixedSignNumberEditField(
                value = amount,
                negativeSign = false,
                onValueChange = {
                    onBalanceChanged(it)
                }
            )
            Text(
                text = "Balance after: $balanceAfter VND",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Name", style = titleTextStyle)
            OutlinedTextField(
                value = name,
                onValueChange = onNameChanged,
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
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
            if (!isAddingSession) {
                SectionDividerWithText(text = "or")
            } else {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            OpenSessionSection(
                isAddingSession = isAddingSession,
                paymentPerMember = 20,
                startDateTime = LocalDateTime.now(),
                endDateTime = LocalDateTime.now(),
                onIsAddingSessionDisplayed = { isAddingSession = it },
                onPaymentPerMemberChanged = {},
                onStartDateTimeChanged = {},
                onEndDateTimeChanged = {},
                onCreate = {},
                modifier = Modifier.fillMaxWidth()
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSessionSection(
    isAddingSession: Boolean,
    paymentPerMember: Int,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    onPaymentPerMemberChanged: (Int) -> Unit,
    onIsAddingSessionDisplayed: (Boolean) -> Unit,
    onStartDateTimeChanged: (LocalDateTime) -> Unit,
    onEndDateTimeChanged: (LocalDateTime) -> Unit,
    onCreate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val titleTextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
    )
    var startDatePickerDisplayed by remember { mutableStateOf(false) }
    var endDatePickerDisplayed by remember { mutableStateOf(false) }
    val startDatePickerState = rememberDatePickerState()
    val endDatePickerState = rememberDatePickerState()
    if (startDatePickerDisplayed) {
        DatePickerDialog(
            onDismissRequest = { startDatePickerDisplayed = false },
            confirmButton = {
                Button(onClick = {
                    if (startDatePickerState.selectedDateMillis == null) {
                        onStartDateTimeChanged(
                            LocalDateTime.now()
                        )
                    } else {
                        onStartDateTimeChanged(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(startDatePickerState.selectedDateMillis!!),
                                ZoneId.systemDefault(),
                            )
                        )
                    }
                    startDatePickerDisplayed = false
                }) {
                    Text(text = "Submit")
                }
            }) {
            DatePicker(state = endDatePickerState)
        }
    }
    if (endDatePickerDisplayed) {
        DatePickerDialog(
            onDismissRequest = { endDatePickerDisplayed = false },
            confirmButton = {
                Button(onClick = {
                    if (endDatePickerState.selectedDateMillis == null) {
                        onEndDateTimeChanged(
                            LocalDateTime.now()
                        )
                    } else {
                        onEndDateTimeChanged(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(endDatePickerState.selectedDateMillis!!),
                                ZoneId.systemDefault(),
                            )
                        )
                    }
                    endDatePickerDisplayed = false
                }) {
                    Text(text = "Submit")
                }
            }) {
            DatePicker(state = endDatePickerState)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        if (!isAddingSession) {
            Button(
                onClick = {
                    onIsAddingSessionDisplayed(true)
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Open collect session")
            }
        } else {
            Text(
                text = "Collect Session Details",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Payments"
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = paymentPerMember.toString(),
                        onValueChange = {
                            onPaymentPerMemberChanged(it.toInt())
                        },
                        prefix = {
                            Text(text = "VND", style = MaterialTheme.typography.labelSmall)
                        },
                        textStyle = TextStyle.Default.copy(
                            textAlign = TextAlign.Right,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.widthIn(100.dp, 120.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "/member")
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Start at", style = titleTextStyle)
                OutlinedButton(
                    onClick = { startDatePickerDisplayed = true },
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
                        Text(text = startDateTime.format(dateFormatter))
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Last until", style = titleTextStyle)
                OutlinedButton(
                    onClick = { endDatePickerDisplayed = true },
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
                        Text(text = endDateTime.format(dateFormatter))
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedButton(
                onClick = {
                    onIsAddingSessionDisplayed(false)
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Turn back to balance entry")
            }
        }
    }
}