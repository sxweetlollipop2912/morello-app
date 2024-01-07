package com.example.morello.ui.session_detail


import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.CollectSessionDetail
import com.example.morello.data_layer.repositories.CollectSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

val CollectSessionDetail.dueDays: Int
    get() = ChronoUnit.DAYS.between(OffsetDateTime.now(), due).toInt()

data class SessionDetailUiState(
    val sessionDetail: CollectSessionDetail,
    val searchQuery: String = "",
) {
    companion object {
        val empty = SessionDetailUiState(
            sessionDetail = CollectSessionDetail(
                id = -1,
                name = "",
                description = "",
                start = OffsetDateTime.now(),
                due = OffsetDateTime.now(),
                isOpen = false,
                paymentPerMember = 0,
                paidCount = 0,
                memberCount = 0,
                currentAmount = 0,
                expectedAmount = 0,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                memberStatuses = emptyList(),
            ),
        )
    }
}

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    private val collectSessionRepository: CollectSessionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var sessionId: Int? = null
    val groupId = savedStateHandle.get<Int>(OwnerGroupHomeRoute.groupId)!!

    fun setSessionId(sessionId: Int) {
        this.sessionId = sessionId
        refreshUiState()
    }


    private var _uiState = MutableStateFlow(SessionDetailUiState.empty)
    val uiState = _uiState.transform { uiState ->
        emit(uiState.copy(
            sessionDetail = uiState.sessionDetail.copy(
                memberStatuses = uiState.sessionDetail.memberStatuses
                    .filter { it.name.contains(uiState.searchQuery, ignoreCase = true) }
                    .sortedBy { memberStatus -> memberStatus.status }
            )
        ))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = SessionDetailUiState.empty,
    )

    fun onMemberStatusToggled(memberId: Int) {
        viewModelScope.launch {
            collectSessionRepository.updateCollectEntryStatus(
                groupId,
                sessionId!!,
                memberId,
                !uiState.value.sessionDetail.memberStatuses.first { it.id == memberId }.status
            )
            refreshUiState()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun refreshUiState() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    sessionDetail = collectSessionRepository.getCollectionSessionDetail(
                        groupId,
                        sessionId!!
                    )
                )
            }
        }
    }
}
