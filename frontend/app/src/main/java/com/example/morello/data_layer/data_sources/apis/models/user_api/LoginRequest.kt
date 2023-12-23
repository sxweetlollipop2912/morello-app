package com.example.morello.data_layer.data_sources.apis.models.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String,
)
