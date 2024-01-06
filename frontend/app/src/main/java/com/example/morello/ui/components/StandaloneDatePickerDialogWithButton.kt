package com.example.morello.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandaloneDatePickerDialogWithButton(
    onDismissRequest: () -> Unit,
    onDateTimeChanged: (OffsetDateTime) -> Unit,
    fallbackDateTime: OffsetDateTime? = null,
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                if (datePickerState.selectedDateMillis == null) {
                    onDateTimeChanged(fallbackDateTime ?: OffsetDateTime.now())
                } else {
                    onDateTimeChanged(
                        OffsetDateTime.ofInstant(
                            Instant.ofEpochMilli(datePickerState.selectedDateMillis!!),
                            ZoneId.systemDefault(),
                        )
                    )
                }
                onDismissRequest()
            }) {
                Text(text = "Submit")
            }
        }) {
        DatePicker(state = datePickerState)
    }
}