package com.example.morello.ui.authorized_home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.morello.data_layer.data_sources.data_types.NewGroup


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
        onProfileIconClicked = {},
        onCreateNewGroup = onCreateNewGroup,
        onReloadGroups = viewModel::reload,
    )
}