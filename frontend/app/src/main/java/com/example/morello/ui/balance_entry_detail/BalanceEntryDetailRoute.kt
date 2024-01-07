package com.example.morello.ui.balance_entry_detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun BalanceEntryDetailRoute(
    groupId: Int,
    entryId: Int,
    viewModel: BalanceEntryDetailViewModel,
    onBack: () -> Unit,
    modifier: Modifier,
) {
    val uiState = viewModel.uiState
    LaunchedEffect(key1 = uiState.state) {
        when (uiState.state) {
            State.Uninitialized -> {
                viewModel.init(groupId, entryId)
            }

            else -> {}
        }
    }
    BalanceEntryDetailScreen(
        uiState = uiState,
        onBack = onBack,
        modifier = modifier,
    )
}