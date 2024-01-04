package com.example.morello.data_layer.data_sources.data_types.groups

import com.fasterxml.jackson.annotation.JsonProperty
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSession
import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.user.User
import java.time.LocalDateTime

data class GroupList(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("is_leader") val isLeader: Boolean,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime
)

data class GroupCreate(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String
)

data class GroupDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime,
    @JsonProperty("recent_open_sessions") val recentOpenSessions: List<CollectSession>,
    @JsonProperty("recent_balance_entries") val recentBalanceEntries: List<BalanceEntry>,
    @JsonProperty("leader") val leader: User
)

data class GroupUpdate(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String
)