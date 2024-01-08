package com.example.morello.ui.group_settings

import GroupMembersRoute
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GroupMembersRoute(
    viewModel: GroupSettingsViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.reload()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GroupMembersScreen(
        uiState = uiState,
        onBack = onBack,
        onAddMember = viewModel::addMember,
        onRemoveMember = viewModel::removeMember
    )
}