package com.example.morello.data_layer.data_sources.data_types.balance

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BalanceEntry(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("recorded_at") val recordedAt: LocalDateTime,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime,
)

data class BalanceEntryDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("recorded_at") val recordedAt: LocalDateTime,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime,
)

data class BalanceEntryCreate(
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("recorded_at") val recordedAt: LocalDateTime,
)

data class BalanceEntryUpdate(
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
)