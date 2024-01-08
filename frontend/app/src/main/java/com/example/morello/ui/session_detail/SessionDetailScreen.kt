package com.example.morello.ui.session_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.morello.data_layer.data_types.formattedWithSymbol
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailScreen(
    uiState: SessionDetailUiState,
    onRefreshUiState: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onToggleMemberStatus: (Int) -> Unit,
    onCloseSession: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (
        sessionDetail,
        searchQuery,
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
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = sessionDetail.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    if (sessionDetail.isOpen) {
                        ClickableText(
                            text = AnnotatedString("Close session"),
                            onClick = { onCloseSession() },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.error,
                            )
                        )
                    } else {
                        Text(
                            text = "Session closed",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.outline,
                                fontStyle = FontStyle.Italic
                            )
                        )
                    }
                },
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .nestedScroll(refreshState.nestedScrollConnection)
        ) {
            if (!refreshState.isRefreshing) {
                Column(
                    Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = sessionDetail.currentAmount.formattedWithSymbol(),
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Black
                        )
                    )
                    Text(
                        text = "over ${sessionDetail.expectedAmount.formattedWithSymbol()} collected",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.displaySmall.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    ).toSpanStyle()
                                ) {
                                    append("${sessionDetail.paidCount}")
                                }
                                withStyle(
                                    style = MaterialTheme.typography.displaySmall.copy(
                                        color = MaterialTheme.colorScheme.secondary
                                    ).toSpanStyle()
                                ) {
                                    append("/${sessionDetail.memberCount}")
                                }
                                withStyle(
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.5f
                                        )
                                    ).toSpanStyle()
                                ) {
                                    append(" paid")
                                }
                            },
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.5f
                                    )
                                ).toSpanStyle()
                            ) {
                                append("Payment: ")
                            }
                            withStyle(
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ).toSpanStyle()
                            ) {
                                append(sessionDetail.paymentPerMember.formattedWithSymbol())
                            }
                            withStyle(
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.5f
                                    )
                                ).toSpanStyle()
                            ) {
                                append(" / member")
                            }
                        },
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = onSearchQueryChanged,
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            placeholder = { Text("Search member") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {}
                        Spacer(modifier = Modifier.size(8.dp))

                        val scrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            if (!refreshState.isRefreshing) {
                                sessionDetail.memberStatuses.forEach { member ->
                                    MemberStatusEntry(
                                        name = member.name,
                                        onClick = {
                                            if (sessionDetail.isOpen) {
                                                onToggleMemberStatus(member.id)
                                            }
                                        },
                                        status = member.status,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp, bottom = 8.dp)
                                    )
                                    HorizontalDivider()
                                }
                                Spacer(modifier = Modifier.size(50.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
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
fun MemberStatusEntry(
    name: String,
    status: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
        }
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                contentColor = if (!status) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary.copy(
                    alpha = 0.5f
                ),
                containerColor = if (!status) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            ),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (status) "Paid" else "Unpaid",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
