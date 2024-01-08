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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
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
import com.example.morello.data_layer.data_types.Member
import kotlinx.coroutines.launch

@Composable
fun GroupMembersScreen(
    uiState: GroupSettingsUiState,
    onAddMember: (String) -> Unit,
    onRemoveMember: (Int) -> Unit,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    val (_, members, _) = uiState
    Scaffold(
        topBar = {
            GroupMembersTopBar(
                title = "Members",
                onBack = onBack,
                onAddMember = { showDialog = true })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            members.forEach { member ->
                MemberItem(member = member, onRemoveMember = onRemoveMember)
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
        if (showDialog) {
            AddMemberDialog(onAddMember = onAddMember, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun MemberItem(
    member: Member,
    onRemoveMember: (Int) -> Unit,
) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp), text = member.name
        )
        IconButton(onClick = { onRemoveMember(member.id) }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Member")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupMembersTopBar(
    title: String,
    onBack: () -> Unit,
    onAddMember: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
        IconButton(onClick = onAddMember) {
            Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Add Member")
        }
    }, modifier = modifier
    )
}

@Composable
fun AddMemberDialog(
    onAddMember: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var member by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Member") },
        text = {
            TextField(
                value = member,
                onValueChange = { member = it },
                placeholder = { Text(text = "Member Name") }
            )
        },
        confirmButton = {
            Button(onClick = {
                scope.launch {
                    onAddMember(member)
                    onDismiss()
                }
            }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Cancel")
            }
        }
    )
}