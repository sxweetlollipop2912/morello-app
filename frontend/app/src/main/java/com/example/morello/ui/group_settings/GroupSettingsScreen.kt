package com.example.morello.ui.group_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddModerator
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GroupSettingsScreen(
    uiState: GroupSettingsUiState,
    onEditGroupInfo: (String, String) -> Unit,
    onBack: () -> Unit,
    navToMembers: () -> Unit,
    navToModerators: () -> Unit
) {
    val (groupInfo, _, _) = uiState
    var editing by remember { mutableStateOf(false) }
    var groupName by remember(groupInfo.name) { mutableStateOf(groupInfo.name) }
    var groupDescription by remember(groupInfo.description) { mutableStateOf(groupInfo.description) }

    Scaffold(
        topBar = {
            GroupSettingsTopBar("Settings", onBack = onBack)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    BasicTextField(
                        value = groupName,
                        onValueChange = { groupName = it },
                        readOnly = !editing,
                        textStyle = MaterialTheme.typography.headlineMedium
                    )
                    BasicTextField(
                        value = groupDescription,
                        onValueChange = { groupDescription = it },
                        readOnly = !editing,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        if (editing) {
                            onEditGroupInfo(groupName, groupDescription)
                        }
                        editing = !editing
                    },
                    shape = MaterialTheme.shapes.small,
                ) {
                    if (editing) {
                        Text(text = "Save")
                    } else {
                        Text(text = "Edit")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                SettingsItem(
                    modifier = Modifier,
                    title = "Members",
                    icon = {
                        Icon(imageVector = Icons.Default.People, contentDescription = "See members")
                    },
                    onClick = navToMembers
                )
                SettingsItem(
                    title = "Manage Moderators",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddModerator,
                            contentDescription = "Manage moderators"
                        )
                    },
                    onClick = navToModerators
                )
                SettingsItem(
                    modifier = Modifier,
                    title = "Leave Group",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "See members"
                        )
                    },
                    onClick = { /*TODO*/ })
            }
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) { icon() }
        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSettingsTopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        modifier = modifier
    )
}