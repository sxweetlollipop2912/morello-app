package com.example.morello.data_layer.data_sources.data_types.user

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(
    @JsonProperty("refresh") val refresh: String,
)

data class RefreshTokenResponse(
    @JsonProperty("access") val access: String,
)