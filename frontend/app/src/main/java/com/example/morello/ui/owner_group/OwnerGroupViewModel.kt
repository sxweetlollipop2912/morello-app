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
        val name: String,
        val currentBalance: Currency,
        val expectedBalance: Currency,
    )
    data class CollectSessionInfo(
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
                name = "",
                currentBalance = 0f,
                expectedBalance = 0f,
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
                    name = "$groupId",
                    currentBalance = 100f,
                    expectedBalance = 200f,
                ),
                subCollections = listOf(
                    OwnerGroupData.CollectSessionInfo(
                        name = "Session 1",
                        description = "Collect session description",
                        dueDate = LocalDateTime.now(),
                        dueDays = 10,
                        isOpen = true,
                        paidCount = 1,
                        memberCount = 2,
                        currentAmount = 100f,
                        expectedAmount = 200f,
                    ),
                    OwnerGroupData.CollectSessionInfo(
                        name = "Session 2",
                        description = "Collect session description",
                        dueDate = LocalDateTime.now(),
                        dueDays = 10,
                        isOpen = false,
                        paidCount = 1,
                        memberCount = 2,
                        currentAmount = 100f,
                        expectedAmount = 200f,
                    ),
                ),
                subBalanceEntries = listOf(
                    OwnerGroupData.BalanceEntryInfo(
                        name = "Entry 1",
                        amount = 100f,
                        description = "Balance entry description",
                        recordedAt = LocalDateTime.now(),
                    ),
                    OwnerGroupData.BalanceEntryInfo(
                        name = "Entry 2",
                        amount = -100f,
                        description = "Balance entry description",
                        recordedAt = LocalDateTime.now(),
                    ),
                ),
            )
        }
    }
}