package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class BalanceEntry(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("current_amount") val currentAmount: Int,
    @JsonProperty("expected_amount") val expectedAmount: Int,
    @JsonProperty("description") val description: String,
    @JsonProperty("created_at") val createdAt: String,
    @JsonProperty("session") val session: CollectSessionEntry?,
)
