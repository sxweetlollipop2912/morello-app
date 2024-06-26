package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("email") val email: String,
)

data class RegisterResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("email") val email: String,
    @JsonProperty("name") val name: String,
)