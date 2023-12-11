package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class CollectSessionEntry(
    @JsonProperty("id") val id: Int,
    @JsonProperty("collect_session") val collectSession: String,
    @JsonProperty("member") val member: String,
    @JsonProperty("paid") val paid: Boolean,
    @JsonProperty("amount") val amount: Int,
)
