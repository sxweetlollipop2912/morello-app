package com.example.morello.ui.balance_entry_list

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.BalanceEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class BalanceEntryListUiState(
    val entries: List<BalanceEntry>,
    val searchQuery: String = "",
) {
    companion object {
        val empty = BalanceEntryListUiState(
            entries = emptyList(),
        )
    }
}

@HiltViewModel
class BalanceEntryListViewModel @Inject constructor(
//    val balanceEntryRepository: BalanceEntryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val groupId = savedStateHandle.get<Int>(OwnerGroupHomeRoute.groupId)!!

    private var _entries = MutableStateFlow<List<BalanceEntry>>(emptyList())

    private var _uiState = MutableStateFlow(BalanceEntryListUiState.empty)
    val uiState = _uiState.combine(_entries) { uiState, entries ->
        val filteredEntries = entries.filter {
            it.name.contains(uiState.searchQuery, ignoreCase = true)
        }
        uiState.copy(
            entries = filteredEntries,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BalanceEntryListUiState.empty,
    )

    init {
        refreshUiState()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun refreshUiState() {
        viewModelScope.launch {
            // TODO: Mock data only
            _entries.update {
                listOf(
                    BalanceEntry(
                        id = 1,
                        name = "Entry 1",
                        amount = 100000,
                        description = "Description 1",
                        recordedAt = LocalDateTime.now(),
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    BalanceEntry(
                        id = 2,
                        name = "Entry 2",
                        amount = -200000,
                        description = "Description 2",
                        recordedAt = LocalDateTime.now(),
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    BalanceEntry(
                        id = 3,
                        name = "Entry 3",
                        amount = 300000,
                        description = "Description 3",
                        recordedAt = LocalDateTime.now(),
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    BalanceEntry(
                        id = 4,
                        name = "Entry 4",
                        amount = -400000,
                        description = "Description 4",
                        recordedAt = LocalDateTime.now(),
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    BalanceEntry(
                        id = 5,
                        name = "Entry 5",
                        amount = 500000,
                        description = "Description 5",
                        recordedAt = LocalDateTime.now(),
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                )
            }
        }
    }
}