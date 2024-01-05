package com.example.morello.ui.create_balance_entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.Currency
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
    Error,
    TryToGoBack,
    ConfirmGoBack,
}

data class CreateExpenseUiState(
    val amount: Currency,
    val balanceAfter: Currency,
    val name: String,
    val description: String,
    val dateTime: LocalDateTime,
    val state: State = State.Idle,
    val error: String? = null,
) {
    companion object {
        val new = CreateExpenseUiState(
            amount = 0,
            balanceAfter = 0,
            name = "",
            description = "",
            dateTime = LocalDateTime.now(),
        )
    }
}

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(
        CreateExpenseUiState.new
    )
    val uiState = _uiState.asStateFlow()

    fun reset() {
        _uiState.value = CreateExpenseUiState.new
    }

    private fun isEmpty(): Boolean {
        val (
            amount,
            balanceAfter,
            name,
            description,
        ) = _uiState.value
        return amount == 0 && name == "" && description == ""
    }

    fun tryToGoBack() {
        if (!isEmpty()) {
            _uiState.value = _uiState.value.copy(state = State.TryToGoBack)
        } else {
            _uiState.value = _uiState.value.copy(state = State.ConfirmGoBack)
        }
    }

    fun confirmGoBack() {
        if (_uiState.value.state == State.TryToGoBack) {
            _uiState.value = _uiState.value.copy(state = State.ConfirmGoBack)
        } else {
            throw IllegalStateException(
                "confirm_go_back() called when state is not TryToGoBack"
            )
        }
    }

    fun cancelGoBack() {
        if (_uiState.value.state == State.TryToGoBack) {
            _uiState.value = _uiState.value.copy(state = State.Idle)
        } else {
            throw IllegalStateException(
                "cancel_go_back() called when state is not ConfirmGoBack"
            )
        }
    }

    fun updateAmount(amount: Currency) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    fun updateBalanceAfter(balanceAfter: Currency) {
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

    fun submit(groupId: Int) {
        _uiState.value = _uiState.value.copy(state = State.Submitting)
        viewModelScope.launch {
            try {
//                val rs = groupRepository.createBalanceEntry(groupId, _uiState.value.let {
//                    BalanceEntryCreate(
//                        name = it.name,
//                        description = it.description,
//                        amount = it.amount,
//                        createdAt = LocalDateTime.now()
//                    )
//                })
                _uiState.value = _uiState.value.copy(state = State.Success)
            } catch (e: Exception) {
//                _uiState.value = _uiState.value.copy(state = State.Error, error = e.msg)
            }
        }
    }
}