package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class Balance(
    @JsonProperty("current_balance") val currentBalance: Currency,
    @JsonProperty("expected_balance") val expectedBalance: Currency,
)