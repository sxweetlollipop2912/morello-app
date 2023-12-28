package com.example.morello.data_layer.data_sources.data_types.balance

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class UpdateBalanceEntryRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
)

data class UpdateBalanceEntryResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
)