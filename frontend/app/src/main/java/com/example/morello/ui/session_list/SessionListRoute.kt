package com.example.morello.ui.session_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SessionListRoute(
    viewModel: SessionListViewModel,
    onSessionClicked: (Int) -> Unit,
    onCreateNewSession: () -> Unit,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.refreshUiState()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SessionListScreen(
        uiState = uiState,
        onRefreshUiState = viewModel::refreshUiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSessionClicked = onSessionClicked,
        onCreateNewSession = onCreateNewSession,
        onBack = onBack,
    )
}