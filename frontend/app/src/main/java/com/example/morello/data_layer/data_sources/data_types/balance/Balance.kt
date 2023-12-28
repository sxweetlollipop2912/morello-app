package com.example.morello.data_layer.data_sources.data_types.balance

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty

data class Balance(
    @JsonProperty("current_balance") val currentBalance: Currency,
    @JsonProperty("expected_balance") val expectedBalance: Currency,
)