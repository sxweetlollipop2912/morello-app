package com.example.morello.ui.owner_group

import OwnerGroupHomeRoute
import androidx.lifecycle.SavedStateHandle
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

data object OwnerGroupData {
    data class GroupInfo(
        val id: Int,
        val name: String,
        val currentBalance: Currency,
        val expectedBalance: Currency,
    )

    data class CollectSessionInfo(
        val id: Int,
        val name: String,
        val description: String,
        val dueDate: LocalDateTime,
        val dueDays: Int,
        val isOpen: Boolean,
        val paidCount: Int,
        val memberCount: Int,
        val currentAmount: Currency,
        val expectedAmount: Currency,
    )

    data class BalanceEntryInfo(
        val id: Int,
        val name: String,
        val amount: Currency,
        val description: String,
        val recordedAt: LocalDateTime,
    )
}

data class OwnerGroupUiState(
    val group: OwnerGroupData.GroupInfo,
    val subCollections: List<OwnerGroupData.CollectSessionInfo>,
    val subBalanceEntries: List<OwnerGroupData.BalanceEntryInfo>,
) {
    companion object {
        val empty = OwnerGroupUiState(
            group = OwnerGroupData.GroupInfo(
                id = -1,
                name = "",
                currentBalance = 0,
                expectedBalance = 0,
            ),
            subCollections = emptyList(),
            subBalanceEntries = emptyList(),
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
    val uiState = _uiState.asStateFlow()

    init {
        refreshUiState()
    }

    fun refreshUiState() {
        viewModelScope.launch {
            // TODO: This is mock data
            _uiState.value = OwnerGroupUiState(
                group = OwnerGroupData.GroupInfo(
                    id = groupId,
                    name = "$groupId",
                    currentBalance = 1000000,
                    expectedBalance = 2000000,
                ),
                subCollections = listOf(
                    OwnerGroupData.CollectSessionInfo(
                        id = 1,
                        name = "Session 1",
                        description = "Collect session description",
                        dueDate = LocalDateTime.now(),
                        dueDays = 10,
                        isOpen = true,
                        paidCount = 1,
                        memberCount = 2,
                        currentAmount = 100000,
                        expectedAmount = 200000,
                    ),
                    OwnerGroupData.CollectSessionInfo(
                        id = 2,
                        name = "Session 2",
                        description = "Collect session description",
                        dueDate = LocalDateTime.now(),
                        dueDays = 10,
                        isOpen = false,
                        paidCount = 1,
                        memberCount = 2,
                        currentAmount = -100000,
                        expectedAmount = -200000,
                    ),
                ),
                subBalanceEntries = listOf(
                    OwnerGroupData.BalanceEntryInfo(
                        id = 1,
                        name = "Entry 1",
                        amount = 10000,
                        description = "Balance entry very long description",
                        recordedAt = LocalDateTime.now(),
                    ),
                    OwnerGroupData.BalanceEntryInfo(
                        id = 2,
                        name = "Entry 2",
                        amount = -100000,
                        description = "Balance entry description",
                        recordedAt = LocalDateTime.now(),
                    ),
                ),
            )
        }
    }
}