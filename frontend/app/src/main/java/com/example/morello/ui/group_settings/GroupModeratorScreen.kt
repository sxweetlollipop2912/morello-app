package com.example.morello.ui.group_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.Moderator

@Composable
fun GroupModeratorsScreen(
    uiState: GroupSettingsUiState,
    onBack: () -> Unit,
) {
    val (_, _, moderators) = uiState
    Scaffold(
        topBar = {
            GroupSettingsTopBar("Members", onBack = onBack)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            moderators.forEach { mod ->
                ModeratorItem(moderator = mod)
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ModeratorItem(
    moderator: Moderator,
) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { /*TODO*/ }
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp), text = moderator.userEmail
        )
    }
}