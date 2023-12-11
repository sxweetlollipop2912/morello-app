package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String,
)
