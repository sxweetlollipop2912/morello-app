package com.example.morello.data_layer.data_sources.data_types.members

import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSessionEntry
import com.fasterxml.jackson.annotation.JsonProperty

data class Member(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean,
)
