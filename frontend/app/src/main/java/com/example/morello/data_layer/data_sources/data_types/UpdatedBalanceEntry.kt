package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdatedBalanceEntry(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("expected_amount") val expectedAmount: Int,
)
