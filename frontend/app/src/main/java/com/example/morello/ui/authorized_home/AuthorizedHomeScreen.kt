package com.example.morello.ui.authorized_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizedHomeScreen(
    uiState: AuthorizedHomeUiState,
    onCreateNewGroup: () -> Unit,
    onProfileIconClicked: () -> Unit,
    onGroupSelect: (groupId: Int) -> Unit,
    onReloadGroups: () -> Unit,
) {
    val (user, groups, state) = uiState
    val username = user.name
    val sideDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = sideDrawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            bottomStart = 0.dp,
                            topEnd = 16.dp,
                            bottomEnd = 16.dp,
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                    )
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max)
            ) {
                Button(
                    onClick = onProfileClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Switch to non-admin mode",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Switch to non-admin mode",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Log out",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = MaterialTheme.colorScheme.error
                                ),
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Log out",
                                tint = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }
            }
        }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    actions = {
                        IconButton(onClick = onProfileIconClicked) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    sideDrawerState.open()
                                }
                            }, modifier = Modifier.border(
                                1.dp, MaterialTheme.colorScheme.onSurface,
                                MaterialTheme.shapes.small
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Menu")
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.titleMedium.toSpanStyle().copy(
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                ) {
                                    append("Hello, ")
                                }
                                withStyle(
                                    style = MaterialTheme.typography.titleMedium.toSpanStyle().copy(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    append(username)
                                }
                            })
                        }
                    })
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding),
            ) {
                SearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Text(text = "My Groups")
                    IconButton(onClick = onCreateNewGroup) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Group",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onReloadGroups) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload")
                    }
                }
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    groups.forEach { group ->
                        GroupListEntry(
                            name = group.name,
                            type = "Owner",
                            onClick = {
                                onGroupSelect(group.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GroupListEntry(
    name: String,
    type: String,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .border(1.dp, MaterialTheme.colorScheme.onSurface, MaterialTheme.shapes.small)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = type,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}