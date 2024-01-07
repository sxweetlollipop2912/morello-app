package com.example.morello.ui.session_list

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.repositories.CollectSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit.DAYS
import javax.inject.Inject

val CollectSession.dueDays: Int
    get() = DAYS.between(OffsetDateTime.now(), due).toInt()

data class SessionListUiState(
    val overdueSessions: List<CollectSession>,
    val ongoingSessions: List<CollectSession>,
    val closedSessions: List<CollectSession>,
    val searchQuery: String = "",
) {
    companion object {
        val empty = SessionListUiState(
            overdueSessions = emptyList(),
            ongoingSessions = emptyList(),
            closedSessions = emptyList(),
        )
    }
}


@HiltViewModel
class SessionListViewModel @Inject constructor(
    val sessionRepository: CollectSessionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val groupId = savedStateHandle.get<Int>(OwnerGroupHomeRoute.groupId)!!

    private var _sessions = MutableStateFlow<List<CollectSession>>(emptyList())

    private var _uiState = MutableStateFlow(SessionListUiState.empty)
    val uiState = _uiState.combine(_sessions) { uiState, sessions ->
        val filteredSessions = sessions.filter {
            it.name.contains(uiState.searchQuery, ignoreCase = true)
        }
        uiState.copy(
            overdueSessions = filteredSessions.filter { it.isOpen && it.dueDays <= 0 }
                .sortedBy { it.dueDays },
            ongoingSessions = filteredSessions.filter { it.isOpen && it.dueDays > 0 }
                .sortedBy { it.dueDays },
            closedSessions = filteredSessions.filter { !it.isOpen }
                .sortedByDescending { it.updatedAt },
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SessionListUiState.empty,
    )

    init {
        refreshUiState()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun refreshUiState() {
        viewModelScope.launch {
            _sessions.update { sessionRepository.getCollectSessions(groupId) }
        }
    }
}