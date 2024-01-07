package com.example.morello.ui.balance_entry_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BalanceEntryListRoute(
    viewModel: BalanceEntryListViewModel,
    onBalanceEntryClicked: (Int) -> Unit,
    onCreateNewBalanceEntry: () -> Unit,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.refreshUiState()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BalanceEntryListScreen(
        uiState = uiState,
        onRefreshUiState = viewModel::refreshUiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onBalanceEntryClicked = onBalanceEntryClicked,
        onCreateNewBalanceEntry = onCreateNewBalanceEntry,
        onBack = onBack,
    )
}