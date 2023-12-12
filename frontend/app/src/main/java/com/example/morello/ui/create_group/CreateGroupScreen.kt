package com.example.morello.ui.create_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    uiState: CreateGroupUiState,
    onGroupNameChanged: (String) -> Unit,
    onMembersListChanged: (List<String>) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
) {
    var isAddingMember by rememberSaveable { mutableStateOf(false) }
    var editingMemberName by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                        Text(text = "Create Group")
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
            Modifier.padding(it)
        ) {
            Text(text = "Group Name")
            TextField(
                placeholder = { Text(text = "My Group") },
                value = uiState.groupName,
                onValueChange = onGroupNameChanged,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Members (${uiState.membersList.size})")
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
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
    if (isAddingMember) {
        AddMemberDialog(
            currentEditingMemberName = editingMemberName,
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
                TextField(
                    value = currentEditingMemberName,
                    onValueChange = {
                        onEditingMemberNameChanged(it)
                    },
                    label = { Text(text = "Member Name") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onAddMember()
                            onDismissRequest()
                            onEditingMemberNameChanged("")
                        }
                    ),
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
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = memberName)
            IconButton(onClick = onRemoveClicked) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove Member")
            }
        }
    }
}