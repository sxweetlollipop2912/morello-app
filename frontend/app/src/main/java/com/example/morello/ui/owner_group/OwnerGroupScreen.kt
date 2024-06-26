package com.example.morello.ui.owner_group

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
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
    onSettings: () -> Unit,
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
        isLeader,
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
    var fabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
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
            }, actions = {
                IconButton(onClick = onSettings) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Settings")
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
    val interactionSource = remember {
        MutableInteractionSource()
    }
    if (fabExpanded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    fabExpanded = false
                }
                .background(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                )
        ) {
        }
    }
    if (isLeader) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = !fabExpanded,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    FloatingActionButton(
                        onClick = { fabExpanded = true },
                        shape = RoundedCornerShape(50.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Expand",
                        )
                    }
                }
            }
            val density = LocalDensity.current
            AnimatedVisibility(
                visible = fabExpanded,
                enter = expandVertically() + fadeIn(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(16.dp)
                ) {
                    val spacing = 10.dp
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "New Expense",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        FloatingActionButton(
                            onClick = onAddNewExpenseEntry,
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.TrendingDown,
                                contentDescription = "Add new expense"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(spacing))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "New Income",
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        FloatingActionButton(
                            onClick = onAddNewIncomeEntry,
                            shape = RoundedCornerShape(50.dp),
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.TrendingUp,
                                contentDescription = "Add new income"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(spacing))
                    FloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { fabExpanded = false },
                        shape = RoundedCornerShape(50.dp),
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Collapse")
                    }
                }
            }
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
                    if (session.dueDays < 0) {
                        Text(
                            text = "${-session.dueDays} day(s) overdue",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    } else {
                        Text(
                            text = "Due in ${session.dueDays} day(s)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
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
                    } else {
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
