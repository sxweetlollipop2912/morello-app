package com.example.morello.ui.group_settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GroupSettingsRoute(
    viewModel: GroupSettingsViewModel,
    onBack: () -> Unit,
    navToMembers: () -> Unit,
    navToModerators: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.reload()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GroupSettingsScreen(
        uiState = uiState,
        onBack = onBack,
        navToMembers = navToMembers,
        navToModerators = navToModerators
    )
}