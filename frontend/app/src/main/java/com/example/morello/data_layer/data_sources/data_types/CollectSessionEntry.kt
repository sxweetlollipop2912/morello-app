package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class CollectSessionEntry(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("collect_session") val collectSession: String,
    @JsonProperty("paid") val paid: Boolean,
    @JsonProperty("is_open") val isOpen: Boolean,
    @JsonProperty("due") val due: String,
    @JsonProperty("due_amount") val dueAmount: Int,
)
