package com.example.morello.data_layer.data_sources.apis.models.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(
    @JsonProperty("access") val access: String,
    @JsonProperty("refresh") val refresh: String,
)
