package com.example.morello.data_layer.data_sources.data_types.groups

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty

data class GroupDetails (
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("member_count") val memberCount: Int,
    @JsonProperty("balance") val balance: GroupBalance,
    @JsonProperty("recent_open_sessions") val recentOpenSessions: List<RecentOpenSession>,
    @JsonProperty("recent_balance_entries") val recentBalanceEntries: List<RecentBalanceEntry>,
)

data class GroupBalance (
    @JsonProperty("current") val current: Currency,
    @JsonProperty("expected") val expected: Currency,
)

data class RecentOpenSession (
    @JsonProperty("id") val id: Int,
)

data class RecentBalanceEntry (
    @JsonProperty("id") val id: Int,
)