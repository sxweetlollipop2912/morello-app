package com.example.morello.data_layer.data_sources.data_types.collect_sessions

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class CollectSessionEntry(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("collect_session") val collectSession: String,
    @JsonProperty("paid") val paid: Boolean,
    @JsonProperty("is_open") val isOpen: Boolean,
    @JsonProperty("due") val due: LocalDateTime,
    @JsonProperty("due_amount") val dueAmount: Currency,
)
