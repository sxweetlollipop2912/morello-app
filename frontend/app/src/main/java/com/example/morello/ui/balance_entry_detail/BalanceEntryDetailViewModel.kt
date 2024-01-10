package com.example.morello.ui.balance_entry_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.Currency
import com.example.morello.data_layer.repositories.CollectSessionRepository
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

enum class State {
    Uninitialized,
    Loading,
    Initialized,
}

data class BalanceEntryDetailUiState(
    val state: State = State.Uninitialized,
    val amount: Currency,
    val name: String,
    val description: String,
    val date: OffsetDateTime,
) {
    companion object {
        val Empty = BalanceEntryDetailUiState(
            state = State.Uninitialized,
            amount = 0,
            name = "",
            description = "",
            date = OffsetDateTime.now(),
        )
    }
}

@HiltViewModel
class BalanceEntryDetailViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val sessionRepository: CollectSessionRepository,
) : ViewModel() {
    var uiState: BalanceEntryDetailUiState by mutableStateOf(BalanceEntryDetailUiState.Empty)
        private set

    fun finish() {
        uiState = uiState.copy(state = State.Uninitialized)
    }

    fun init(groupId: Int, entryId: Int) {
        uiState = uiState.copy(state = State.Loading)
        viewModelScope.launch {
            val balanceEntry = groupRepository.getBalanceEntry(groupId, entryId)
            uiState = uiState.copy(
                state = State.Initialized,
                amount = balanceEntry.amount,
                name = balanceEntry.name,
                description = balanceEntry.description,
                date = balanceEntry.recordedAt,
            )
        }
    }
}