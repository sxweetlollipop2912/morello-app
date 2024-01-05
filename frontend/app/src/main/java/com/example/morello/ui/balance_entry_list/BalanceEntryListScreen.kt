package com.example.morello.ui.balance_entry_list

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.BalanceEntry
import com.example.morello.data_layer.data_types.formattedNoTime
import com.example.morello.data_layer.data_types.formattedWithSymbol
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceEntryListScreen(
    uiState: BalanceEntryListUiState,
    onRefreshUiState: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onBalanceEntryClicked: (Int) -> Unit,
    onCreateNewBalanceEntry: () -> Unit,
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
        entries,
        searchQuery,
    ) = uiState

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Balance entries",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
            }, actions = {
                IconButton(onClick = onCreateNewBalanceEntry) {
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
                    placeholder = { Text("Search entry") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth()
                ) {}
                Spacer(modifier = Modifier.size(16.dp))
                if (!refreshState.isRefreshing) {
                    BalanceEntryListScreenContent(
                        entries = entries,
                        onEntryClicked = onBalanceEntryClicked,
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
fun BalanceEntryListScreenContent(
    entries: List<BalanceEntry>,
    onEntryClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        entries.forEach { entry ->
            BalanceEntryListItem(
                entry = entry,
                onClick = { onEntryClicked(entry.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BalanceEntryListItem(
    entry: BalanceEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = entry.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )
            Text(
                text = entry.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Column(
            Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            val amount = entry.amount
            Text(
                text = if (amount > 0) {
                    "+${amount.formattedWithSymbol()}"
                } else {
                    amount.formattedWithSymbol()
                },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = entry.recordedAt.formattedNoTime(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}