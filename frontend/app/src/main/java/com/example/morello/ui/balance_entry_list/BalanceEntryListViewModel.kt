package com.example.morello.ui.balance_entry_list

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.BalanceEntry
import com.example.morello.data_layer.repositories.BalanceEntryRepository
import com.example.morello.data_layer.repositories.GroupRepository
import com.example.morello.data_layer.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BalanceEntryListUiState(
    val isLeader: Boolean = false,
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
    private val balanceEntryRepository: BalanceEntryRepository,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val groupId = savedStateHandle.get<Int>(OwnerGroupHomeRoute.groupId)!!

    private var _entries = MutableStateFlow<List<BalanceEntry>>(emptyList())

    private var _uiState = MutableStateFlow(BalanceEntryListUiState.empty)
    val uiState = _uiState.combine(_entries) { uiState, entries ->
        val filteredEntries = entries.filter {
            it.name.contains(uiState.searchQuery, ignoreCase = true) ||
                    it.description.contains(uiState.searchQuery, ignoreCase = true)
        }
        uiState.copy(
            entries = filteredEntries.sortedByDescending { it.recordedAt },
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
            val groupDetail = groupRepository.getGroupDetail(groupId)
            val userDetail = userRepository.fetchUserDetail()
            val isLeader = groupDetail.leader.id == userDetail.id
            _entries.update { balanceEntryRepository.getBalanceEntries(groupId) }
            _uiState.update {
                it.copy(
                    isLeader = isLeader,
                )
            }
        }
    }
}