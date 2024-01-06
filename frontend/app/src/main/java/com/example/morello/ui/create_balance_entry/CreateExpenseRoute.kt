package com.example.morello.ui.create_balance_entry

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay


@Composable
fun CreateExpenseRoute(
    groupId: Int,
    viewModel: CreateExpenseViewModel,
    onBack: () -> Unit,
    modifier: Modifier,
) {
    val uiState = viewModel.uiState
    LaunchedEffect(uiState.state) {
        when (uiState.state) {
            State.Uninitialized -> {
                viewModel.init()
            }

            State.Success -> {
                onBack()
                delay(1000)
                viewModel.finish()
            }

            else -> {}
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
        onConfirmGoBack = onBack,
        modifier = modifier,
    )
}