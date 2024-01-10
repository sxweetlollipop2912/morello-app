package com.example.morello.ui.balance_entry_detail

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
    LaunchedEffect(key1 = groupId, key2 = entryId) {
        when (uiState.state) {
            State.Uninitialized -> {
                viewModel.init(groupId, entryId)
            }

            else -> {}
        }
    }
    BalanceEntryDetailScreen(
        uiState = uiState,
        onBack = {
            viewModel.finish()
            onBack()
        },
        modifier = modifier,
    )
}