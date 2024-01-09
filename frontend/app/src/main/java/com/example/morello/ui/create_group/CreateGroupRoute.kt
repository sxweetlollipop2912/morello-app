package com.example.morello.ui.create_group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun CreateGroupRoute(
    viewModel: CreateGroupViewModel,
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
    CreateGroupScreen(
        uiState = uiState,
        onGroupNameChanged = viewModel::onGroupNameChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onMembersListChanged = viewModel::onMembersListChanged,
        onSubmit = {
            viewModel.onSubmit()
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