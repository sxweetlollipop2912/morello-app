package com.example.morello.ui.create_balance_entry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.util.Date

data class CreateExpenseUiState(
    val amount: Int,
    val balanceAfter: Int,
    val name: String,
    val description: String,
    val dateTime: LocalDateTime,
)

class CreateExpenseViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(
        CreateExpenseUiState(
            amount = 0,
            balanceAfter = 0,
            name = "",
            description = "",
            dateTime = LocalDateTime.now(),
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateAmount(amount: Int) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    fun updateBalanceAfter(balanceAfter: Int) {
        _uiState.value = _uiState.value.copy(balanceAfter = balanceAfter)
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateDateTime(dateTime: LocalDateTime) {
        _uiState.value = _uiState.value.copy(dateTime = dateTime)
    }

    fun submit() {
    }
}