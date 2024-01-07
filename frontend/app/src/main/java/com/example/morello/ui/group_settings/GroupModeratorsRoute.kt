package com.example.morello.ui.group_settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GroupModeratorsRoute(
    groupId: Int,
    viewModel: GroupSettingsViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.reload()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GroupModeratorsScreen(uiState = uiState, onBack = onBack)
}