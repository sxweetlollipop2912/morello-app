package com.example.morello.ui.owner_group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OwnerGroupRoute(
    viewModel: OwnerGroupViewModel,
    onSettings: () -> Unit,
    onNewIncomeEntry: () -> Unit,
    onNewExpenseEntry: () -> Unit,
    onBalanceEntryList: () -> Unit,
    onCollectSessionList: () -> Unit,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.refreshUiState()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OwnerGroupScreen(
        uiState = uiState,
        onSettings = onSettings,
        onRefreshUiState = viewModel::refreshUiState,
        onAddNewExpenseEntry = onNewExpenseEntry,
        onAddNewIncomeEntry = onNewIncomeEntry,
        onSeeBalanceEntryClicked = {},
        onSeeAllBalanceEntryClicked = onBalanceEntryList,
        onSeeCollectSessionClicked = {},
        onSeeAllCollectSessionClicked = onCollectSessionList,
        onBack = onBack,
    )
}