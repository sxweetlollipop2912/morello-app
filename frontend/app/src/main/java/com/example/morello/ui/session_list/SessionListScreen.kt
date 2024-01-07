package com.example.morello.ui.session_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.data_types.formattedNoTime
import com.example.morello.data_layer.data_types.formattedWithSymbol
import com.example.morello.ui.components.BaseCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionListScreen(
    uiState: SessionListUiState,
    onRefreshUiState: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSessionClicked: (Int) -> Unit,
    onCreateNewSession: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val refreshScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    if (refreshState.isRefreshing) {
        refreshScope.launch {
            // TODO
            delay(500)
            onRefreshUiState()
            refreshState.endRefresh()
        }
    }

    val (
        overdueSessions,
        ongoingSessions,
        closedSessions,
        searchQuery,
    ) = uiState

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Collect sessions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
            }, actions = {
                IconButton(onClick = onCreateNewSession) {
                    Icon(Icons.Default.Add, contentDescription = "New collect session")
                }
            })
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .nestedScroll(refreshState.nestedScrollConnection)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChanged,
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text("Search session") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth()
                ) {}
                Spacer(modifier = Modifier.size(16.dp))
                if (!refreshState.isRefreshing) {
                    SessionListScreenContent(
                        overdueSessions = overdueSessions,
                        ongoingSessions = ongoingSessions,
                        closedSessions = closedSessions,
                        onSessionClicked = onSessionClicked,
                    )
                }
                Spacer(Modifier.height(36.dp))
            }

            PullToRefreshContainer(
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
fun SessionListScreenContent(
    overdueSessions: List<CollectSession>,
    ongoingSessions: List<CollectSession>,
    closedSessions: List<CollectSession>,
    onSessionClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        OverdueSessionsCard(
            sessions = overdueSessions,
            onSessionClicked = onSessionClicked,
        )
        Spacer(modifier = Modifier.size(16.dp))
        OngoingSessionsCard(
            sessions = ongoingSessions,
            onSessionClicked = onSessionClicked,
        )
        Spacer(modifier = Modifier.size(16.dp))
        ClosedSessionsCard(
            sessions = closedSessions,
            onSessionClicked = onSessionClicked,
        )
    }
}

@Composable
fun ClosedSessionListItem(
    session: CollectSession,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = session.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )
            Text(
                text = "Last update on ${session.updatedAt.formattedNoTime()}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
            Text(
                text = "${session.paidCount}/${session.memberCount} collected",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            val currentAmount = session.currentAmount
            Text(
                text = if (currentAmount >= 0) {
                    "+${currentAmount.formattedWithSymbol()}"
                } else {
                    currentAmount.formattedWithSymbol()
                },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            val expectedAmount = session.expectedAmount
            Text(
                text = "Expected: ${expectedAmount.formattedWithSymbol()}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun OverdueSessionListItem(
    session: CollectSession,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = session.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )
            Text(
                text = "${-session.dueDays} day(s) overdue",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.error
                )
            )
            Text(
                text = "${session.memberCount - session.paidCount} pending payment(s)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            val currentAmount = session.currentAmount
            if (currentAmount >= 0) {
                Text(
                    text = "+${currentAmount.formattedWithSymbol()}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            } else {
                Text(
                    text = currentAmount.formattedWithSymbol(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }

            val expectedAmount = session.expectedAmount
            Text(
                text = "Expected: ${expectedAmount.formattedWithSymbol()}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun OngoingSessionListItem(
    session: CollectSession,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = session.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )
            Text(
                text = "Due in ${session.dueDays} day(s)",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "${session.memberCount - session.paidCount} pending payment(s)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            val currentAmount = session.currentAmount
            Text(
                text = if (currentAmount >= 0) {
                    "+${currentAmount.formattedWithSymbol()}"
                } else {
                    currentAmount.formattedWithSymbol()
                },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            val expectedAmount = session.expectedAmount
            Text(
                text = "Expected: ${expectedAmount.formattedWithSymbol()}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@Composable
fun OverdueSessionsCard(
    sessions: List<CollectSession>,
    modifier: Modifier = Modifier,
    onSessionClicked: (Int) -> Unit,
) {
    BaseCard(
        title = {
            Text(
                text = "Overdue (${sessions.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.error
                )
            )
        },
        builder = { session ->
            OverdueSessionListItem(session = session)
        },
        items = sessions,
        getId = { it.id },
        onSeeIndividual = onSessionClicked,
        seeAllDisabled = true,
        modifier = modifier,
    )
}

@Composable
fun OngoingSessionsCard(
    sessions: List<CollectSession>,
    modifier: Modifier = Modifier,
    onSessionClicked: (Int) -> Unit,
) {
    BaseCard(
        title = {
            Text(
                text = "Ongoing (${sessions.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        builder = { session ->
            OngoingSessionListItem(session = session)
        },
        items = sessions,
        getId = { it.id },
        onSeeIndividual = onSessionClicked,
        seeAllDisabled = true,
        modifier = modifier,
    )
}

@Composable
fun ClosedSessionsCard(
    sessions: List<CollectSession>,
    modifier: Modifier = Modifier,
    onSessionClicked: (Int) -> Unit,
) {
    BaseCard(
        title = {
            Text(
                text = "Closed (${sessions.size})",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        builder = { session ->
            ClosedSessionListItem(session = session)
        },
        items = sessions,
        getId = { it.id },
        onSeeIndividual = onSessionClicked,
        seeAllDisabled = true,
        modifier = modifier,
    )
}