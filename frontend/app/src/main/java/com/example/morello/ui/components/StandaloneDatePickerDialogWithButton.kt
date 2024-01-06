package com.example.morello.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandaloneDatePickerDialogWithButton(
    onDismissRequest: () -> Unit,
    onDateTimeChanged: (LocalDateTime) -> Unit,
    fallbackDateTime: LocalDateTime? = null,
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                if (datePickerState.selectedDateMillis == null) {
                    onDateTimeChanged(fallbackDateTime ?: LocalDateTime.now())
                } else {
                    onDateTimeChanged(
                        LocalDateTime.ofInstant(
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