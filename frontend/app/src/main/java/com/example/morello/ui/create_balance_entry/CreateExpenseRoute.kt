package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun CreateExpenseRoute(
    groupId: Int,
    viewModel: CreateExpenseViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.state) {
        if (uiState.state == State.Success) {
            onBack()
            viewModel.reset()
        } else if (uiState.state == State.ConfirmGoBack) {
            onBack()
            viewModel.reset()
        }
    }
    CreateExpenseScreen(
        uiState = uiState,
        onAmountChanged = viewModel::updateAmount,
        onNameChanged = viewModel::updateName,
        onDescriptionChanged = viewModel::updateDescription,
        onDateTimeChanged = viewModel::updateDateTime,
        onCreate = {
            viewModel.submit(groupId)
        },
        onBack = {
            viewModel.tryToGoBack()
        },
        onConfirmGoBack = {
            viewModel.confirmGoBack()
        },
        onCancelGoBack = {
            viewModel.cancelGoBack()
        }
    )
}