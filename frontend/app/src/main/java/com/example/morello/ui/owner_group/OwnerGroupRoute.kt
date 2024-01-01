package com.example.morello.ui.owner_group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun OwnerGroupRoute(
    viewModel: OwnerGroupViewModel,
    onToNewIncomeEntry: () -> Unit,
    onToNewExpenseEntry: () -> Unit,
    onToBalanceEntryList: () -> Unit,
    onToCollectSessionList: () -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    OwnerGroupScreen(
        uiState = uiState,
        onRefreshUiState = viewModel::refreshUiState,
        onAddNewExpenseEntry = onToNewExpenseEntry,
        onAddNewIncomeEntry = onToNewIncomeEntry,
        onSeeAllBalanceEntryClicked = onToBalanceEntryList,
        onSeeAllCollectSessionClicked = onToCollectSessionList,
        onBack = onBack,
    )
}