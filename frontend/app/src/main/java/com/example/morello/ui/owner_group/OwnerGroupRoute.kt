package com.example.morello.ui.owner_group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.morello.data_layer.data_sources.data_types.NewBalanceEntry

@Composable
fun OwnerGroupRoute(
    groupId: Int,
    viewModel: OwnerGroupViewModel,
    onAddNewIncomeEntry: () -> Unit,
    onAddNewExpenseEntry: () -> Unit,
    onAddNewMember: () -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    OwnerGroupScreen(
        uiState = uiState,
        onAddNewExpenseEntry = onAddNewExpenseEntry,
        onAddNewMember = onAddNewMember,
        onSeeAllMemberClicked = {},
        onSeeAllModeratorClicked = {},
        onSeeAllTransactionClicked = {},
        onSeeAllCollectSessionClicked = {},
        onAddNewIncomeEntry = onAddNewIncomeEntry,
        onAddNewModerator = {},
        onBack = onBack,
    )
}