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
            viewModel.reload()
            onBack()
        }
    }
    CreateGroupScreen(
        uiState = uiState,
        onGroupNameChanged = viewModel::onGroupNameChanged,
        onMembersListChanged = viewModel::onMembersListChanged,
        onSubmit = {
            viewModel.onSubmit()
            onBack()
        },
        onBack = {
            viewModel.reload()
            onBack()
        }
    )
}