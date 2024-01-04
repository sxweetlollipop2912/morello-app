package com.example.morello.ui.owner_group

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.formatted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerGroupScreen(
    uiState: OwnerGroupUiState,
    onRefreshUiState: () -> Unit,
    onSeeAllBalanceEntryClicked: () -> Unit,
    onSeeAllCollectSessionClicked: () -> Unit,
    onAddNewExpenseEntry: () -> Unit,
    onAddNewIncomeEntry: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var fabExpanded by remember { mutableStateOf(false) }
    val (
        group,
        subCollections,
        subBalanceEntries,
    ) = uiState
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .offset(20.dp, 20.dp)
            ) {
                if (!fabExpanded) {
                    FloatingActionButton(
                        onClick = { fabExpanded = true },
                        modifier = Modifier.padding(40.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Expand")
                    }
                } else {
                    Column(modifier = Modifier.padding(40.dp)) {
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        FloatingActionButton(onClick = onAddNewExpenseEntry) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        FloatingActionButton(onClick = onAddNewIncomeEntry) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        FloatingActionButton(onClick = { fabExpanded = false }) {
                            Icon(Icons.Default.Clear, contentDescription = "Close")
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
    ) {
        Column(
            Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Text(text = "Group balance")
            Text(text = group.currentBalance.formatted(), style = MaterialTheme.typography.displayMedium)
            Text(text = "After collecting: ${group.expectedBalance.formatted()}")
            CollectSessionsCard(
                onSeeAll = onSeeAllCollectSessionClicked,
                collectSessions = subCollections, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            BalanceEntriesCard(
                onSeeAll = onSeeAllBalanceEntryClicked,
                balanceEntries = subBalanceEntries,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.padding(60.dp))
        }

    }
}

@Composable
fun <T> BaseCard(
    name: String,
    items: List<T>,
    builder: @Composable (T) -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        modifier = modifier.clickable(onClick = onSeeAll),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = name)
            Divider()
            items.forEach { item ->
                builder(item)
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "See all")
            }
        }
    }
}

@Composable
fun CollectSessionsCard(
    collectSessions: List<OwnerGroupData.CollectSessionInfo>,
    modifier: Modifier = Modifier,
    onSeeAll: () -> Unit,
) {
    BaseCard(
        name = "Collect sessions",
        builder = { collectSessionInfo ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(text = collectSessionInfo.name)
                    Text(text = "Due in ${collectSessionInfo.dueDays} days")
                    Text(text = "${collectSessionInfo.memberCount - collectSessionInfo.paidCount} pending payments")
                }
                val currentAmount = collectSessionInfo.currentAmount
                val expectedAmount = collectSessionInfo.expectedAmount
                val valueText = if (expectedAmount > 0) {
                    "+${currentAmount.formatted()}/${expectedAmount.formatted()}"
                } else {
                    "${currentAmount.formatted()}/${expectedAmount.formatted()}"
                }
                Text(text = valueText)
            }
            HorizontalDivider()
        },
        items = collectSessions,
        onSeeAll = onSeeAll,
        modifier = modifier,
    )
}

@Composable
fun BalanceEntriesCard(
    balanceEntries: List<OwnerGroupData.BalanceEntryInfo>,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        name = "Balance entries",
        items = balanceEntries,
        builder = { balanceEntry ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column {
                    Text(text = balanceEntry.name)
                    Text(text = balanceEntry.recordedAt.toString())
                }
                val valueText = if (balanceEntry.amount > 0) {
                    "+${balanceEntry.amount.formatted()}"
                } else {
                    balanceEntry.amount.formatted()
                }
                Text(text = valueText)
            }
            HorizontalDivider()
        },
        onSeeAll = onSeeAll,
        modifier = modifier
    )
}
