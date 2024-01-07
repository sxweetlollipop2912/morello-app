package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class BalanceEntry(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("recorded_at") val recordedAt: OffsetDateTime,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime,
)

data class BalanceEntryDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("recorded_at") val recordedAt: OffsetDateTime,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime,
)

data class BalanceEntryCreate(
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("recorded_at") val recordedAt: OffsetDateTime,
)

data class BalanceEntryUpdate(
    @JsonProperty("name") val name: String,
    @JsonProperty("amount") val amount: Currency,
    @JsonProperty("description") val description: String,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
)