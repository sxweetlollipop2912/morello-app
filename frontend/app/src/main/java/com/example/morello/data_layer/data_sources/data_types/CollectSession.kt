package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class CollectSession(
    @JsonProperty("id") val id: String,
    @JsonProperty("start") val start: String,
    @JsonProperty("due") val due: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("is_open") val isOpen: Boolean,
)
