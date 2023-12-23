package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun CreateExpenseRoute(
    viewModel: CreateExpenseViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    CreateExpenseScreen(
        amount = uiState.amount,
        balanceAfter = uiState.balanceAfter,
        name = uiState.name,
        description = uiState.description,
        dateTime = uiState.dateTime,
        onAmountChanged = viewModel::updateAmount,
        onNameChanged = viewModel::updateName,
        onDescriptionChanged = viewModel::updateDescription,
        onDateTimeChanged = viewModel::updateDateTime,
        onCreate = viewModel::submit,
        onBack = onBack,
    )
}