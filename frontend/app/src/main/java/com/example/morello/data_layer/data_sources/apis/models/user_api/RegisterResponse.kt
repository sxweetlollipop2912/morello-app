package com.example.morello.data_layer.data_sources.apis.models.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("email") val email: String,
    @JsonProperty("name") val name: String,
)