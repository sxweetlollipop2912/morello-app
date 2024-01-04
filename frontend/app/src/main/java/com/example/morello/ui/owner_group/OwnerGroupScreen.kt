package com.example.morello.ui.owner_group

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import com.example.morello.data_layer.data_sources.data_types.formattedNoTime
import com.example.morello.data_layer.data_sources.data_types.formattedWithSymbol
import kotlinx.coroutines.delay
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
        group,
        subCollections,
        subBalanceEntries,
    ) = uiState

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
                Text(text = group.name)
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
                    text = group.currentBalance.formattedWithSymbol(),
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "After collecting: ${group.expectedBalance.formattedWithSymbol()}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.padding(8.dp))

                CollectSessionsCard(
                    onSeeSession = onSeeCollectSessionClicked,
                    onSeeAll = onSeeAllCollectSessionClicked,
                    collectSessions = subCollections,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(8.dp))

                BalanceEntriesCard(
                    onSeeBalanceEntry = onSeeBalanceEntryClicked,
                    onSeeAll = onSeeAllBalanceEntryClicked,
                    balanceEntries = subBalanceEntries,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(60.dp))
            }

            PullToRefreshContainer(
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
fun <T> BaseCard(
    name: String,
    items: List<T>,
    getId: (T) -> Int,
    builder: @Composable (T) -> Unit,
    onSeeIndividual: (Int) -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier,
    ) {
        Column {
            val horizontalPaddingValue = 15.dp
            val verticalPaddingValue = 8.dp
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(
                    top = verticalPaddingValue,
                    start = horizontalPaddingValue,
                    end = horizontalPaddingValue,
                )
            )

            Spacer(modifier = Modifier.height(verticalPaddingValue))
            HorizontalDivider()

            items.forEach { item ->
                HorizontalDivider()

                Box(
                    modifier = Modifier
                        .clickable(onClick = { onSeeIndividual(getId(item)) })
                        .padding(
                            horizontal = horizontalPaddingValue,
                            vertical = verticalPaddingValue
                        )
                ) {
                    builder(item)
                }
            }

            HorizontalDivider()

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .clickable(onClick = onSeeAll)
                    .fillMaxWidth()
                    .padding(
                        horizontal = horizontalPaddingValue,
                        vertical = verticalPaddingValue
                    )
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                )
            }
        }
    }
}

@Composable
fun CollectSessionsCard(
    collectSessions: List<OwnerGroupData.CollectSessionInfo>,
    modifier: Modifier = Modifier,
    onSeeSession: (Int) -> Unit,
    onSeeAll: () -> Unit,
) {
    BaseCard(
        name = "Collect sessions",
        builder = { collectSessionInfo ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = collectSessionInfo.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                    )
                    Text(
                        text = "Due in ${collectSessionInfo.dueDays} day(s)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                    Text(
                        text = "${collectSessionInfo.memberCount - collectSessionInfo.paidCount} pending payment(s)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    val currentAmount = collectSessionInfo.currentAmount
                    Text(
                        text = if (currentAmount > 0) {
                            "+${currentAmount.formattedWithSymbol()}"
                        } else {
                            currentAmount.formattedWithSymbol()
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    val expectedAmount = collectSessionInfo.expectedAmount
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
    balanceEntries: List<OwnerGroupData.BalanceEntryInfo>,
    onSeeBalanceEntry: (Int) -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        name = "Balance entries",
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
