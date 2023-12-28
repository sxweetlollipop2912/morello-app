package com.example.morello.data_layer.data_sources.data_types.user

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateUserRequest (
    @JsonProperty("name") val name: String,
    @JsonProperty("password") val password: String,
)

data class UpdateUserResponse (
    @JsonProperty("name") val name: String,
)