package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class Member(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("total_due_amount") val totalDueAmount: Int,
    @JsonProperty("related_sessions") val relatedSessions: List<CollectSessionEntry>,
    @JsonProperty("is_archived") val isArchived: Boolean,
)
