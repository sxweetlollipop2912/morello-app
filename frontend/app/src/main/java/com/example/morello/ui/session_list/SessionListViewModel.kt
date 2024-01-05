package com.example.morello.ui.session_list

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.data_types.Currency
import com.example.morello.data_layer.repositories.CollectSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

val CollectSession.dueDays: Int
    get() = ChronoUnit.DAYS.between(LocalDateTime.now(), due).toInt()

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
        // TODO: mock data only so date is all wrong
        uiState.copy(
            overdueSessions = filteredSessions,
            ongoingSessions = filteredSessions,
            closedSessions = filteredSessions,
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
            // TODO: mock data only
            _sessions.update {
                listOf(
                    CollectSession(
                        id = 1,
                        name = "Session 1",
                        description = "This is a description",
                        start = LocalDateTime.now(),
                        due = LocalDateTime.now(),
                        isOpen = true,
                        paidCount = 2,
                        memberCount = 3,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                        paymentPerMember = 10000,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    CollectSession(
                        id = 2,
                        name = "Session 2",
                        description = "This is a description",
                        start = LocalDateTime.now(),
                        due = LocalDateTime.now(),
                        isOpen = true,
                        paidCount = 2,
                        memberCount = 3,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                        paymentPerMember = 10000,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    CollectSession(
                        id = 3,
                        name = "Session 3",
                        description = "This is a description",
                        start = LocalDateTime.now(),
                        due = LocalDateTime.now(),
                        isOpen = true,
                        paidCount = 2,
                        memberCount = 3,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                        paymentPerMember = 10000,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    CollectSession(
                        id = 4,
                        name = "Session 4",
                        description = "This is a description",
                        start = LocalDateTime.now(),
                        due = LocalDateTime.now(),
                        isOpen = true,
                        paidCount = 2,
                        memberCount = 3,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                        paymentPerMember = 10000,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    CollectSession(
                        id = 5,
                        name = "Session 5",
                        description = "This is a description",
                        start = LocalDateTime.now(),
                        due = LocalDateTime.now(),
                        isOpen = true,
                        paidCount = 2,
                        memberCount = 3,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                        paymentPerMember = 10000,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                    CollectSession(
                        id = 6,
                        name = "Session 6",
                        description = "This is a description",
                        start = LocalDateTime.now(),
                        due = LocalDateTime.now(),
                        isOpen = true,
                        paidCount = 2,
                        memberCount = 3,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                        paymentPerMember = 10000,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    ),
                )
            }
        }
    }
}