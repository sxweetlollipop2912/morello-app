package com.example.morello.ui.session_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun SessionDetailRoute(
    viewModel: SessionDetailViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.refreshUiState()
    }
    SessionDetailScreen(
        uiState = uiState,
        onRefreshUiState = viewModel::refreshUiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onToggleMemberStatus = viewModel::onMemberStatusToggled,
        onBack = onBack,
    )
}