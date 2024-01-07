package com.example.morello.ui.owner_group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OwnerGroupRoute(
    viewModel: OwnerGroupViewModel,
    onNewIncomeEntry: () -> Unit,
    onNewExpenseEntry: () -> Unit,
    onBalanceEntryList: () -> Unit,
    onCollectSessionList: () -> Unit,
    onCollectSessionDetail: (sessionId: Int) -> Unit,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.refreshUiState()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OwnerGroupScreen(
        uiState = uiState,
        onRefreshUiState = viewModel::refreshUiState,
        onAddNewExpenseEntry = onNewExpenseEntry,
        onAddNewIncomeEntry = onNewIncomeEntry,
        onSeeBalanceEntryClicked = {},
        onSeeAllBalanceEntryClicked = onBalanceEntryList,
        onSeeCollectSessionClicked = onCollectSessionDetail,
        onSeeAllCollectSessionClicked = onCollectSessionList,
        onBack = onBack,
    )
}