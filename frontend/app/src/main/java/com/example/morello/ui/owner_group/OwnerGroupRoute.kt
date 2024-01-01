package com.example.morello.ui.owner_group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OwnerGroupRoute(
    viewModel: OwnerGroupViewModel,
    onToNewIncomeEntry: () -> Unit,
    onToNewExpenseEntry: () -> Unit,
    onToBalanceEntryList: () -> Unit,
    onToCollectSessionList: () -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OwnerGroupScreen(
        uiState = uiState,
        onRefreshUiState = viewModel::refreshUiState,
        onAddNewExpenseEntry = onToNewExpenseEntry,
        onAddNewIncomeEntry = onToNewIncomeEntry,
        onSeeBalanceEntryClicked = {},
        onSeeAllBalanceEntryClicked = onToBalanceEntryList,
        onSeeCollectSessionClicked = {},
        onSeeAllCollectSessionClicked = onToCollectSessionList,
        onBack = onBack,
    )
}