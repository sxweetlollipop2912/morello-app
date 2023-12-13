package com.example.morello.ui.owner_group

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

data object OwnerGroupScreen {
    data class CollectSessionInfo(
        val name: String,
        val description: String,
        val dueDays: Int,
        val value: Int,
        val pendingPayments: Int,
    )

    data class MemberInfo(
        val name: String,
        val pendingPayments: Int,
    )

    data class ModeratorInfo(
        val name: String,
    )

    data class TransactionInfo(
        val name: String,
        val value: Int,
        val date: String,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerGroupScreen(
    groupBalance: Int,
    afterCollectingBalance: Int,
    subCollections: List<OwnerGroupScreen.CollectSessionInfo>,
    subTransactions: List<OwnerGroupScreen.TransactionInfo>,
    subMembers: List<OwnerGroupScreen.MemberInfo>,
    subModerators: List<OwnerGroupScreen.ModeratorInfo>,
    onSeeAllModeratorClicked: () -> Unit,
    onSeeAllMemberClicked: () -> Unit,
    onSeeAllTransactionClicked: () -> Unit,
    onSeeAllCollectSessionClicked: () -> Unit,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var fabExpanded by remember { mutableStateOf(false) }
    Scaffold(
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
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Add, contentDescription = "Expand")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        FloatingActionButton(onClick = { /*TODO*/ }) {
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
                Text(text = "Group Name")
            }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Text(text = "Group Balance")
            Text(text = "$groupBalance", style = MaterialTheme.typography.displayMedium)
            Text(text = "After Collecting: $afterCollectingBalance")
            CollectSessionsCard(
                onSeeAll = onSeeAllCollectSessionClicked,
                collectSessions = subCollections, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            TransactionsCard(
                onSeeAll = onSeeAllTransactionClicked,
                transactions = subTransactions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            MembersCard(
                onSeeAll = onSeeAllMemberClicked,
                members = subMembers,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            ModeratorsCard(
                onSeeAll = onSeeAllModeratorClicked,
                moderators = subModerators, modifier = Modifier
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
    collectSessions: List<OwnerGroupScreen.CollectSessionInfo>,
    modifier: Modifier = Modifier,
    onSeeAll: () -> Unit,
) {
    BaseCard(
        name = "Collect Sessions",
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
                    Text(text = "${collectSessionInfo.pendingPayments} pending payments")
                }
                val value = collectSessionInfo.value
                val valueText = if (value > 0) {
                    "+$value"
                } else {
                    "$value"
                }
                Text(text = valueText)
            }
            Divider()
        },
        items = collectSessions,
        onSeeAll = onSeeAll,
        modifier = modifier,
    )
}

@Composable
fun TransactionsCard(
    transactions: List<OwnerGroupScreen.TransactionInfo>,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        name = "Transactions",
        items = transactions,
        builder = { transactionInfo ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column {
                    Text(text = transactionInfo.name)
                    Text(text = transactionInfo.date)
                }
                Text(text = "${transactionInfo.value}")
            }
            Divider()
        },
        onSeeAll = onSeeAll,
        modifier = modifier
    )
}

@Composable
fun MembersCard(
    members: List<OwnerGroupScreen.MemberInfo>,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        name = "Members",
        builder = { memberInfo ->
            val value = memberInfo.pendingPayments
            val valueText = if (value > 0) {
                "+$value"
            } else {
                "$value"
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = memberInfo.name)
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(text = "Pending")
                    Text(text = valueText)
                }
            }
            Divider()
        },
        items = members,
        onSeeAll = onSeeAll,
        modifier = modifier,
    )
}

@Composable
fun ModeratorsCard(
    moderators: List<OwnerGroupScreen.ModeratorInfo>,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        name = "Moderators",
        builder = { moderatorInfo ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column {
                    Text(text = moderatorInfo.name)
                }
            }
            Divider()
        },
        items = moderators,
        onSeeAll = onSeeAll,
        modifier = modifier,
    )
}