package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.BalanceEntryCreate
import com.example.morello.data_layer.data_types.Currency
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

data class CreateExpenseUiState(
    val amount: Currency,
    val balanceAfter: Currency?,
    val name: StringOrError,
    val description: String,
    val dateTime: OffsetDateTime,
    val state: State = State.Uninitialized,
    val error: String? = null,
) {
    companion object {
        val new = CreateExpenseUiState(
            amount = 0,
            balanceAfter = null,
            name = StringOrError("", null),
            description = "",
            dateTime = OffsetDateTime.now(),
        )
    }

    fun considerAsNew(): Boolean {
        return amount == 0 && name.value == "" && description == ""
    }
}

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
) : ViewModel() {
    var uiState by mutableStateOf(CreateExpenseUiState.new)
        private set

    private var groupCurrentBalance: Int? = null

    suspend fun init(groupId: Int) {
        groupCurrentBalance = groupRepository.getGroupBalance(groupId).currentBalance
        uiState = uiState.copy(balanceAfter = groupCurrentBalance?.let { it - uiState.amount })
    }

    private fun isEmpty(): Boolean {
        val (
            amount,
            _,
            name,
            description,
        ) = uiState
        return amount == 0 && name.value == "" && description == ""
    }

    fun updateAmount(amount: Currency) {
        uiState = uiState.copy(
            balanceAfter = groupCurrentBalance?.let { it - amount },
            amount = amount
        )
    }

    fun finish() {
        uiState = uiState.copy(state = State.Uninitialized)
    }

    fun updateName(name: String) {
        uiState = uiState.copy(name = uiState.name.copy(value = name))
    }

    fun updateDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    fun updateDateTime(dateTime: OffsetDateTime) {
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
            try {
                val rs = groupRepository.createBalanceEntry(groupId, uiState.let {
                    BalanceEntryCreate(
                        name = it.name.value,
                        description = it.description,
                        amount = -it.amount,
                        recordedAt = it.dateTime,
                    )
                })
                uiState = uiState.copy(state = State.Success)
            } catch (e: Exception) {
                uiState = uiState.copy(state = State.Error, error = e.message)
            }
        }
    }
}