package com.example.morello.ui.authorized_home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun AuthorizedHomeRoute(
    viewModel: AuthorizedHomeViewModel,
    onCreateNewGroup: () -> Unit,
    navigateToGroup: (groupId: Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.reload()
    }
    AuthorizedHomeScreen(
        uiState = uiState,
        onGroupSelect = navigateToGroup,
        onProfileClicked = {},
        onCreateNewGroup = onCreateNewGroup,
        onReloadGroups = viewModel::reload,
    )
}