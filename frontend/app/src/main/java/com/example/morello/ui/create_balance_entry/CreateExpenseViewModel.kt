package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_sources.CreateBalanceEntryException
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryRequest
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

enum class State {
    Idle,
    Submitting,
    Success,
    TryToGoBack,
    ConfirmGoBack,
}

data class CreateExpenseUiState(
    val amount: Currency,
    val balanceAfter: Currency,
    val name: StringOrError,
    val description: String,
    val dateTime: LocalDateTime,
    val state: State = State.Idle,
    val error: String? = null,
) {
    companion object {
        val new = CreateExpenseUiState(
            amount = 0f,
            balanceAfter = 0f,
            name = StringOrError("", null),
            description = "",
            dateTime = LocalDateTime.now(),
        )
    }
}

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CreateExpenseUiState.new)
        private set

    fun reset() {
        uiState = CreateExpenseUiState.new
    }

    private fun isEmpty(): Boolean {
        val (
            amount,
            balanceAfter,
            name,
            description,
        ) = uiState
        return amount == 0f && name.value == "" && description == ""
    }

    fun tryToGoBack() {
        uiState = if (!isEmpty()) {
            uiState.copy(state = State.TryToGoBack)
        } else {
            uiState.copy(state = State.ConfirmGoBack)
        }
    }

    fun confirmGoBack() {
        if (uiState.state == State.TryToGoBack) {
            uiState = uiState.copy(state = State.ConfirmGoBack)
        } else {
            throw IllegalStateException(
                "confirm_go_back() called when state is not TryToGoBack"
            )
        }
    }

    fun cancelGoBack() {
        if (uiState.state == State.TryToGoBack) {
            uiState = uiState.copy(state = State.Idle)
        } else {
            throw IllegalStateException(
                "cancel_go_back() called when state is not ConfirmGoBack"
            )
        }
    }

    fun updateAmount(amount: Currency) {
        uiState = uiState.copy(amount = amount)
    }

    fun updateBalanceAfter(balanceAfter: Currency) {
        uiState = uiState.copy(balanceAfter = balanceAfter)
    }

    fun updateName(name: String) {
        uiState = uiState.copy(name = uiState.name.copy(value = name))
    }

    fun updateDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    fun updateDateTime(dateTime: LocalDateTime) {
        uiState = uiState.copy(dateTime = dateTime)
    }

    fun submit(groupId: Int) {
        if (uiState.name.value.isEmpty()) {
            uiState = uiState.copy(
                name = uiState.name.copy(
                    error = "Name should not be empty",
                ),
            )
            return
        }
        uiState = uiState.copy(state = State.Submitting)
        viewModelScope.launch {
            uiState = try {
                val rs = groupRepository.createBalanceEntry(groupId, uiState.let {
                    NewBalanceEntryRequest(
                        name = it.name.value,
                        description = it.description,
                        amount = it.amount,
                        createdAt = LocalDateTime.now()
                    )
                })
                uiState.copy(state = State.Success)
            } catch (e: CreateBalanceEntryException) {
                uiState.copy(state = State.Idle, error = e.message)
            }
        }
    }
}