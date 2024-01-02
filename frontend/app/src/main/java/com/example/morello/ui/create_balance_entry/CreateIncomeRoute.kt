package com.example.morello.ui.create_balance_entry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun CreateIncomeRoute(
    groupId: Int,
    viewModel: CreateIncomeViewModel,
    onBack: () -> Unit,
    modifier: Modifier,
) {
    val uiState = viewModel.uiState
    LaunchedEffect(uiState.state) {
        if (uiState.state == State.Success) {
            onBack()
            viewModel.reset()
        } else if (uiState.state == State.ConfirmGoBack) {
            onBack()
            viewModel.reset()
        }
    }
    CreateIncomeScreen(
        uiState = uiState,
        onBalanceChanged = viewModel::updateAmount,
        onNameChanged = viewModel::updateName,
        onDescriptionChanged = viewModel::updateDescription,
        onDateTimeChanged = viewModel::updateDateTime,
        onCreate = {
            viewModel.submit(groupId)
        },
        onSwitchToCreateNewEntry = viewModel::switchToCreateNewEntry,
        onSwitchToOpenCollectSession = viewModel::switchToCreateNewSession,
        onPaymentPerMemberChanged = viewModel::updateAmountPerMember,
        onStartDateTimeChanged = viewModel::updateStartDateTime,
        onEndDateTimeChanged = viewModel::updateEndDateTime,
        onMemberUpdated = viewModel::updateChosenMember,
        onTryToGoBack = {
            viewModel.tryToGoBack()
        },
        onConfirmGoBack = {
            viewModel.confirmGoBack()
        },
        onCancelGoBack = {
            viewModel.cancelGoBack()
        },
        onDismissDateTimeError = viewModel::dismissDateTimeError,
        modifier = modifier,
    )
}
