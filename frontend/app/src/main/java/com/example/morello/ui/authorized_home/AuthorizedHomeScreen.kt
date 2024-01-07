package com.example.morello.ui.authorized_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizedHomeScreen(
    uiState: AuthorizedHomeUiState,
    onCreateNewGroup: () -> Unit,
    onProfileClicked: () -> Unit,
    onLogOutClicked: () -> Unit,
    onGroupSelect: (groupId: Int) -> Unit,
    onRefreshUiState: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
) {
    val (user, groups, searchQuery, state) = uiState
    val username = user.name
    val sideDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val refreshScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    if (refreshState.isRefreshing) {
        refreshScope.launch {
            onRefreshUiState()
            refreshState.endRefresh()
        }
    }

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
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = "Switch to non-admin mode",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                    Button(
                        onClick = onLogOutClicked,
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
                                imageVector = Icons.AutoMirrored.Default.Logout,
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
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    sideDrawerState.open()
                                }
                            },
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onCreateNewGroup,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .padding(bottom = 16.dp, end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create new group",
                    )
                }
            },
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .nestedScroll(refreshState.nestedScrollConnection)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopCenter),
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = onSearchQueryChanged,
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = { Text("Search group") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        modifier = Modifier.fillMaxWidth()
                    ) {}
                    Spacer(modifier = Modifier.size(16.dp))

                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        if (!refreshState.isRefreshing) {
                            groups.forEach { group ->
                                GroupListEntry(
                                    name = group.name,
                                    description = group.description,
                                    type = if (group.isLeader) "Owner" else "Moderator",
                                    onClick = { onGroupSelect(group.id) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 16.dp)
                                )
                                HorizontalDivider()
                            }
                            Spacer(modifier = Modifier.size(50.dp))
                        }
                    }
                }
                PullToRefreshContainer(
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        }
    }
}

@Composable
fun GroupListEntry(
    name: String,
    description: String,
    type: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = type,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
            )
        }
    }
}