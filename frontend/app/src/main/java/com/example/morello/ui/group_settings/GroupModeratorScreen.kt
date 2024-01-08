package com.example.morello.ui.group_settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddModerator
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.Moderator
import kotlinx.coroutines.launch

@Composable
fun GroupModeratorsScreen(
    uiState: GroupSettingsUiState,
    onAddModerator: (String) -> Unit,
    onRemoveModerator: (Int) -> Unit,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    val (_, _, moderators) = uiState
    Scaffold(topBar = {
        GroupModeratorTopBar(
            title = "Moderators",
            onBack = onBack,
            onAddModerator = {
                showDialog = true
            },
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            moderators.forEach { mod ->
                ModeratorItem(moderator = mod, onRemoveModerator = onRemoveModerator)
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
        if (showDialog) {
            AddModeratorDialog(onAddModerator = onAddModerator, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun ModeratorItem(
    moderator: Moderator,
    onRemoveModerator: (Int) -> Unit,
) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = moderator.userEmail
        )
        IconButton(onClick = {
            onRemoveModerator(moderator.id)
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Moderator")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupModeratorTopBar(
    title: String,
    onBack: () -> Unit,
    onAddModerator: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    TopAppBar(title = {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }, navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
        }
    }, actions = {
        IconButton(
            onClick = onAddModerator
        ) {
            Icon(imageVector = Icons.Default.AddModerator, contentDescription = "Add Moderator")
        }
    }, modifier = modifier
    )
}

@Composable
fun AddModeratorDialog(
    onAddModerator: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(text = "Add Moderator") }, text = {
        TextField(value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "Moderator Email") })
    }, confirmButton = {
        Button(onClick = {
            scope.launch {
                onAddModerator(email)
                onDismiss()
            }
        }) {
            Text(text = "Add")
        }
    }, dismissButton = {
        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Cancel")
        }
    })
}