package com.example.morello.ui.components.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class MemberType {
    OWNER,
    MODERATOR,
    MEMBER,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupEntryCard(
    groupName: String,
    groupDescription: String,
    memberType: MemberType,
    onGroupClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onGroupClicked,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = groupName)
            val typeText = when (memberType) {
                MemberType.OWNER -> "Owner"
                MemberType.MODERATOR -> "Moderator"
                MemberType.MEMBER -> "Member"
            }
            Text(
                text = typeText,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}