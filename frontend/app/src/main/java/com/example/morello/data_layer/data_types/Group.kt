package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Group(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("is_leader") val isLeader: Boolean,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime
)

data class GroupCreate(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String
)

data class GroupDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime,
    @JsonProperty("recent_open_sessions") val recentOpenSessions: List<CollectSession>,
    @JsonProperty("recent_balance_entries") val recentBalanceEntries: List<BalanceEntry>,
    @JsonProperty("leader") val leader: User
)

data class GroupUpdate(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String
)