package com.example.morello.ui.balance_entry_detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun BalanceEntryDetailRoute(
    groupId: Int,
    entryId: Int,
    viewModel: BalanceEntryDetailViewModel,
    onBack: () -> Unit,
) {
    val uiState = viewModel.uiState
    LaunchedEffect(key1 = uiState.state) {
        when (uiState.state) {
            State.Uninitialized -> {
                Log.d("BalanceEntryDetailRoute", "BalanceEntryDetailRoute: $groupId, $entryId")
                viewModel.init(groupId, entryId)
            }

            else -> {}
        }
    }
    BalanceEntryDetailScreen(
        uiState = uiState,
        onBack = onBack,
    )
}