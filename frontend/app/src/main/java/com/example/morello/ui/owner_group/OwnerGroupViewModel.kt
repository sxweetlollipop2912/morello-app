package com.example.morello.ui.owner_group

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morello.data_layer.data_types.Balance
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.data_types.GroupDetail
import com.example.morello.data_layer.data_types.User
import com.example.morello.data_layer.repositories.GroupRepository
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

val CollectSession.dueDays: Int
    get() = ChronoUnit.DAYS.between(OffsetDateTime.now(), due).toInt()

data class OwnerGroupUiState(
    val groupDetail: GroupDetail,
    val groupBalance: Balance,
) {
    companion object {
        val empty = OwnerGroupUiState(
            groupDetail = GroupDetail(
                id = -1,
                name = "",
                description = "",
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                recentOpenSessions = emptyList(),
                recentBalanceEntries = emptyList(),
                leader = User(
                    id = -1,
                    name = "",
                    email = "",
                ),
            ),
            groupBalance = Balance(
                currentBalance = 0,
                expectedBalance = 0,
            ),
        )
    }
}

@HiltViewModel
class OwnerGroupViewModel @Inject constructor(
    val groupRepository: GroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val groupId = savedStateHandle.get<Int>(OwnerGroupHomeRoute.groupId)!!

    private var _uiState = MutableStateFlow(OwnerGroupUiState.empty)
    val uiState = _uiState.transform {
        emit(
            it.copy(
                groupDetail = it.groupDetail.copy(
                    recentOpenSessions = it.groupDetail.recentOpenSessions.sortedBy { it.dueDays },
                    recentBalanceEntries = it.groupDetail.recentBalanceEntries.sortedByDescending { it.recordedAt },
                )
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = OwnerGroupUiState.empty,
    )

    init {
        refreshUiState()
    }

    fun refreshUiState() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    groupDetail = groupRepository.getGroupDetail(groupId),
                    groupBalance = groupRepository.getGroupBalance(groupId),
                )
            }
        }
    }
}