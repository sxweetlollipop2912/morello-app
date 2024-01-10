package com.example.morello.ui.create_group

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.morello.ui.create_balance_entry.handleReopenKeyboard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateGroupScreen(
    uiState: CreateGroupUiState,
    onGroupNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onMembersListChanged: (List<String>) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onConfirmGoBack: () -> Unit,
    onCancelGoBack: () -> Unit,
) {
    var isAddingMember by rememberSaveable { mutableStateOf(false) }
    var editingMemberName by rememberSaveable { mutableStateOf("") }

    BackHandler(onBack = onBack)
    if (uiState.state == State.TryToGoBack) {
        AlertDialog(
            onDismissRequest = { onBack() },
            title = { Text(text = "Discard changes?") },
            text = { Text(text = "Are you sure you want to discard changes?") },
            confirmButton = {
                Button(onClick = { onConfirmGoBack() }) {
                    Text(text = "Discard")
                }
            },
            dismissButton = {
                Button(onClick = { onCancelGoBack() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Cancel Create Group"
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Create Group",
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = onSubmit) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Done Create Group"
                            )
                        }
                    }
                })
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            val titleTextStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            )
            Text(text = "Enter group details", style = titleTextStyle)
            Spacer(modifier = Modifier.padding(3.dp))
            OutlinedTextField(
                label = { Text(text = "Group name") },
                value = uiState.group.name,
                onValueChange = onGroupNameChanged,
                shape = MaterialTheme.shapes.medium,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(5.dp))
            OutlinedTextField(
                label = { Text(text = "Description") },
                value = uiState.group.description,
                onValueChange = onDescriptionChanged,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Members (${uiState.membersList.size})", style = titleTextStyle)
                IconButton(onClick = {
                    isAddingMember = true
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Member")
                }
            }
            for (member in uiState.membersList) {
                MemberListEntry(
                    memberName = member,
                    onRemoveClicked = {
                        onMembersListChanged(uiState.membersList - member)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
    if (isAddingMember) {
        AddMemberDialog(
            currentEditingMemberName = editingMemberName,
            currentNameErrorMessage = { name ->
                if (name.isEmpty()) {
                    "Name cannot be empty"
                } else if (uiState.membersList.contains(name)) {
                    "Name already exists"
                } else {
                    ""
                }
            },
            onEditingMemberNameChanged = { editingMemberName = it },
            onDismissRequest = { isAddingMember = false },
            onAddMember = {
                onMembersListChanged(uiState.membersList + editingMemberName)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AddMemberDialog(
    currentEditingMemberName: String,
    currentNameErrorMessage: (String) -> String,
    onEditingMemberNameChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onAddMember: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Text(text = "Add Member")

                val errorMessage = currentNameErrorMessage(currentEditingMemberName)
                TextField(
                    value = currentEditingMemberName,
                    onValueChange = {
                        onEditingMemberNameChanged(it)
                    },
                    label = { Text(text = "Member Name") },
                    trailingIcon = {
                        if (currentEditingMemberName.isNotEmpty() && errorMessage.isEmpty()) {
                            IconButton(onClick = {
                                onAddMember()
                                onDismissRequest()
                                onEditingMemberNameChanged("")
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Create new member"
                                )
                            }
                        }
                    },
                    isError = errorMessage.isNotEmpty(),
                    supportingText = { Text(text = errorMessage) },
                    modifier = modifier.focusRequester(focusRequester),
                )
            }
        }
    }
}

@Composable
fun MemberListEntry(
    memberName: String,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = memberName)
            IconButton(onClick = onRemoveClicked) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove Member")
            }
        }
    }
}