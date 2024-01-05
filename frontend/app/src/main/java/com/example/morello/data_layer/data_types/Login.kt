package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String,
)

data class LoginResponse(
    @JsonProperty("access") val access: String,
    @JsonProperty("refresh") val refresh: String,
)