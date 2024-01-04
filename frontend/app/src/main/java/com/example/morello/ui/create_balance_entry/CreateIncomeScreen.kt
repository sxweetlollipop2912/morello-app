package com.example.morello.ui.create_balance_entry

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.ui.components.CreateBalanceEntryTopBar
import com.example.morello.ui.components.FixedSignNumberEditField
import com.example.morello.ui.components.SectionDividerWithText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIncomeScreen(
    uiState: CreateIncomeUiState,
    onBalanceChanged: (Currency) -> Unit,
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeChanged: (LocalDateTime) -> Unit,
    onCreate: () -> Unit,
    onSwitchToOpenCollectSession: () -> Unit,
    onSwitchToCreateNewEntry: () -> Unit,
    onPaymentPerMemberChanged: (Currency) -> Unit,
    onStartDateTimeChanged: (LocalDateTime) -> Unit,
    onEndDateTimeChanged: (LocalDateTime) -> Unit,
    onMemberUpdated: (Int, Boolean) -> Unit,
    onTryToGoBack: () -> Unit,
    onConfirmGoBack: () -> Unit,
    onCancelGoBack: () -> Unit,
    onDismissDateTimeError: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (
        name,
        description,
        amount,
        dateTime,
    ) = uiState
    val balanceAfter = amount
    var datePickerDisplayed by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val scrollableState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = uiState.dateTimeError) {
        if (uiState.dateTimeError != null) {
            val snackbarResult = snackbarHostState.showSnackbar("${uiState.dateTimeError}")
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    onDismissDateTimeError()
                }
                SnackbarResult.ActionPerformed -> {
                    onDismissDateTimeError()
                }
            }
        }
    }
    BackHandler(onBack = onTryToGoBack)
    if (uiState.state == State.TryToGoBack) {
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CreateBalanceEntryTopBar(
                isLoading = uiState.state == State.Submitting,
                title = "New income",
                onCreate = onCreate,
                onBack = onTryToGoBack,
            )
        },
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .verticalScroll(scrollableState)
        ) {
            val titleTextStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            )
            if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            FixedSignNumberEditField(
                value = amount,
                negativeSign = false,
                onValueChange = onBalanceChanged,
                modifier = Modifier.fillMaxWidth()
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
                value = name.value,
                isError = name.error != null,
                supportingText = {
                    if (name.error != null) {
                        Text(text = name.error)
                    }

                },
                onValueChange = onNameChanged,
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                trailingIcon = {
                    if (name.error != null) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                        )
                    }
                },
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
            AnimatedVisibility(
                visible = (uiState.mode == Mode.CreateNewEntry),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
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
                                Text(text = dateTime.format(dateFormatter) ?: "")
                            }
                        }
                    }
                    SectionDividerWithText(text = "or")
                    Button(
                        onClick = {
                            onSwitchToOpenCollectSession()
                        },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Open collect session")
                    }
                }
            }
            AnimatedVisibility(
                visible = uiState.mode == Mode.CreateNewSession,
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    OpenSessionSection(
                        paymentPerMember = uiState.createNewSessionData.amountPerMember,
                        startDateTime = uiState.createNewSessionData.startDate,
                        endDateTime = uiState.createNewSessionData.endDate,
                        memberList = uiState.createNewSessionData.memberList,
                        onMemberUpdated = onMemberUpdated,
                        onPaymentPerMemberChanged = onPaymentPerMemberChanged,
                        onStartDateTimeChanged = onStartDateTimeChanged,
                        onEndDateTimeChanged = onEndDateTimeChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedButton(
                        onClick = {
                            onSwitchToCreateNewEntry()
                        },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Turn back to balance entry")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSessionSection(
    paymentPerMember: Currency,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    memberList: List<Pair<String, Boolean>>,
    onPaymentPerMemberChanged: (Currency) -> Unit,
    onStartDateTimeChanged: (LocalDateTime) -> Unit,
    onEndDateTimeChanged: (LocalDateTime) -> Unit,
    onMemberUpdated: (Int, Boolean) -> Unit,
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
    val memberListScrollState = rememberScrollState()
    if (startDatePickerDisplayed) {
        DatePickerDialog(
            onDismissRequest = { startDatePickerDisplayed = false },
            confirmButton = {
                Button(onClick = {
                    if (startDatePickerState.selectedDateMillis == null) {
                        onStartDateTimeChanged(LocalDateTime.now())
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
            DatePicker(state = startDatePickerState)
        }
    }
    if (endDatePickerDisplayed) {
        DatePickerDialog(
            onDismissRequest = { endDatePickerDisplayed = false },
            confirmButton = {
                Button(onClick = {
                    if (endDatePickerState.selectedDateMillis == null) {
                        onEndDateTimeChanged(LocalDateTime.now())
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
            .then(modifier)
            .animateContentSize()
    ) {
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
            Spacer(modifier = Modifier.padding(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                FixedSignNumberEditField(
                    value = paymentPerMember,
                    negativeSign = false,
                    autoFocus = false,
                    prefix = {
                        Text(text = "VND", style = MaterialTheme.typography.labelSmall)
                    },
                    suffix = {
                        Text(text = "/member", style = MaterialTheme.typography.labelSmall)

                    },
                    textStyle = TextStyle.Default.copy(
                        textAlign = TextAlign.Right,
                    ),
                    onValueChange = onPaymentPerMemberChanged,
                )
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
        SectionDividerWithText(text = "Members")
        OutlinedCard(
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 500.dp)
                    .verticalScroll(memberListScrollState)
            ) {
                memberList.forEachIndexed { index, member ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .clickable {
                                onMemberUpdated(index, !member.second)
                            }
                            .fillMaxWidth()
                            .drawBehind {
                                // draw dotted border
                                val strokeWidth = 1.dp.toPx()
                                val dashPathEffect =
                                    androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(10f, 10f),
                                        phase = 0f
                                    )
                                drawLine(
                                    color = Color.DarkGray,
                                    start = Offset(x = 0f, y = size.height - strokeWidth),
                                    end = Offset(x = size.width, y = size.height - strokeWidth),
                                    strokeWidth = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        intervals = floatArrayOf(
                                            10f,
                                            20f
                                        )
                                    ),
                                )
                            }
                            .padding(8.dp)
                    ) {
                        val textStyle = if (member.second) {
                            MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                            )
                        } else {
                            MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                        val decoration = if (member.second) {
                            TextDecoration.None
                        } else {
                            TextDecoration.LineThrough
                        }
                        Text(
                            text = member.first,
                            style = textStyle,
                            textDecoration = decoration
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}