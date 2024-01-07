package com.example.morello.ui.owner_group

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.BalanceEntry
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.data_types.formattedNoTime
import com.example.morello.data_layer.data_types.formattedWithSymbol
import com.example.morello.ui.components.BaseCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerGroupScreen(
    uiState: OwnerGroupUiState,
    onRefreshUiState: () -> Unit,
    onSeeBalanceEntryClicked: (Int) -> Unit,
    onSeeAllBalanceEntryClicked: () -> Unit,
    onSeeCollectSessionClicked: (Int) -> Unit,
    onSeeAllCollectSessionClicked: () -> Unit,
    onAddNewExpenseEntry: () -> Unit,
    onAddNewIncomeEntry: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (
        groupDetail,
        groupBalance,
    ) = uiState

    val refreshScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    if (refreshState.isRefreshing) {
        refreshScope.launch {
            onRefreshUiState()
            refreshState.endRefresh()
        }
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                var fabExpanded by remember { mutableStateOf(false) }

                if (!fabExpanded) {
                    FloatingActionButton(
                        onClick = { fabExpanded = true },
                        shape = RoundedCornerShape(50.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Expand",
                        )
                    }
                } else {
                    Column {
                        val spacing = 10.dp
                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.height(spacing))
                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.height(spacing))
                        FloatingActionButton(
                            onClick = onAddNewExpenseEntry,
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add new expense")
                        }
                        Spacer(modifier = Modifier.height(spacing))
                        FloatingActionButton(
                            onClick = onAddNewIncomeEntry,
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add new income")
                        }
                        Spacer(modifier = Modifier.height(spacing))
                        FloatingActionButton(
                            onClick = { fabExpanded = false },
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Collapse")
                        }
                    }
                }
            }
        },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = groupDetail.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
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

            if (!refreshState.isRefreshing) {
                Column(
                    Modifier
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Group balance",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                    )
                    Text(
                        text = groupBalance.currentBalance.formattedWithSymbol(),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "After collecting: ${groupBalance.expectedBalance.formattedWithSymbol()}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    CollectSessionsCard(
                        onSeeSession = onSeeCollectSessionClicked,
                        onSeeAll = onSeeAllCollectSessionClicked,
                        collectSessions = groupDetail.recentOpenSessions,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    BalanceEntriesCard(
                        onSeeBalanceEntry = onSeeBalanceEntryClicked,
                        onSeeAll = onSeeAllBalanceEntryClicked,
                        balanceEntries = groupDetail.recentBalanceEntries,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(60.dp))
                }
            }

            PullToRefreshContainer(
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
fun CollectSessionsCard(
    collectSessions: List<CollectSession>,
    modifier: Modifier = Modifier,
    onSeeSession: (Int) -> Unit,
    onSeeAll: () -> Unit,
) {
    BaseCard(
        title = {
            Text(
                text = "Collect sessions",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        builder = { session ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
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
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
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
                    }
                    else {
                        Text(
                            text = currentAmount.formattedWithSymbol(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }

                    val expectedAmount = session.expectedAmount
                    Text(
                        text = "Total: ${expectedAmount.formattedWithSymbol()}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        },
        items = collectSessions,
        getId = { it.id },
        onSeeIndividual = onSeeSession,
        onSeeAll = onSeeAll,
        modifier = modifier,
    )
}

@Composable
fun BalanceEntriesCard(
    balanceEntries: List<BalanceEntry>,
    onSeeBalanceEntry: (Int) -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        title = {
            Text(
                text = "Balance entries",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        items = balanceEntries,
        getId = { it.id },
        builder = { balanceEntry ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = balanceEntry.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                    )
                    Text(
                        text = balanceEntry.description,
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
                    val amount = balanceEntry.amount
                    if (amount >= 0) {
                        Text(
                            text = "+${amount.formattedWithSymbol()}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    else {
                        Text(
                            text = amount.formattedWithSymbol(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                    Text(
                        text = balanceEntry.recordedAt.formattedNoTime(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        },
        onSeeIndividual = onSeeBalanceEntry,
        onSeeAll = onSeeAll,
        modifier = modifier
    )
}
