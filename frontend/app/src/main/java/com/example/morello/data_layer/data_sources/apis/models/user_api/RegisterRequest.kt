package com.example.morello.data_layer.data_sources.apis.models.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("email") val email: String,
)