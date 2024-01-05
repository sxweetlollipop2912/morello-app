package com.example.morello.ui.authorized_home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AuthorizedHomeRoute(
    viewModel: AuthorizedHomeViewModel,
    onCreateNewGroup: () -> Unit,
    navigateToGroup: (groupId: Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.refreshUiState()
    }
    AuthorizedHomeScreen(
        uiState = uiState,
        onGroupSelect = navigateToGroup,
        onProfileClicked = {},
        onCreateNewGroup = onCreateNewGroup,
        onReloadGroups = viewModel::refreshUiState,
    )
}