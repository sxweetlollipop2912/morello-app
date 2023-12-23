package com.example.morello.ui.owner_group

import androidx.lifecycle.ViewModel
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class OwnerGroupUiState(
    val groupBalance: Int,
    val afterCollectingBalance: Int,
    val subCollections: List<OwnerGroupScreen.CollectSessionInfo>,
    val subTransactions: List<OwnerGroupScreen.TransactionInfo>,
    val subMembers: List<OwnerGroupScreen.MemberInfo>,
    val subModerators: List<OwnerGroupScreen.ModeratorInfo>,
) {
    companion object {
        val Empty = OwnerGroupUiState(
            groupBalance = 0,
            afterCollectingBalance = 0,
            subCollections = emptyList(),
            subTransactions = emptyList(),
            subMembers = emptyList(),
            subModerators = emptyList(),
        )
    }
}

@HiltViewModel
class OwnerGroupViewModel @Inject constructor(
    groupRepository: GroupRepository,
) : ViewModel() {
    private var _uiState = MutableStateFlow(OwnerGroupUiState.Empty)
    val uiState = _uiState.asStateFlow()
}