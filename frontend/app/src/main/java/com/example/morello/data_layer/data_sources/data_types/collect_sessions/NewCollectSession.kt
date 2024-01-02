package com.example.morello.data_layer.data_sources.data_types.collect_sessions

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty

data class NewCollectSession(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: String,
    @JsonProperty("due") val due: String,
    @JsonProperty("is_open") val isOpen: Boolean,
    @JsonProperty("payment_per_member") val paymentPerMember: Currency,
)