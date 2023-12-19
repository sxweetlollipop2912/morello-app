package com.example.morello.ui.components.group

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateGroupForm(
    groupName: String,
    onGroupNameChanged: (String) -> Unit,
    onGroupDescriptionChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = 8.dp
    Card(modifier = modifier) {
        Text(text = "Create Group")
        TextField(
            value = groupName, onValueChange = onGroupNameChanged,
            label = { Text("Group Name") },
        )
        Spacer(modifier = Modifier.padding(spacing))
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Group")
        }
    }
}