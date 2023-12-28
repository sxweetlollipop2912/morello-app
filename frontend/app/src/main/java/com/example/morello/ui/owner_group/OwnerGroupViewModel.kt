package com.example.morello.ui.owner_group

import androidx.lifecycle.ViewModel
import com.example.morello.data_layer.data_sources.data_types.Currency
import com.example.morello.data_layer.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class OwnerGroupUiState(
    val groupBalance: Currency,
    val afterCollectingBalance: Currency,
    val subCollections: List<OwnerGroupScreen.CollectSessionInfo>,
    val subTransactions: List<OwnerGroupScreen.TransactionInfo>,
    val subMembers: List<OwnerGroupScreen.MemberInfo>,
    val subModerators: List<OwnerGroupScreen.ModeratorInfo>,
) {
    companion object {
        val Empty = OwnerGroupUiState(
            groupBalance = 0f,
            afterCollectingBalance = 0f,
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