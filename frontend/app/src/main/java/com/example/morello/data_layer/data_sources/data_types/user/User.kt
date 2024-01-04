package com.example.morello.data_layer.data_sources.data_types.user

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String
)

data class UserUpdate (
    @JsonProperty("name") val name: String,
    @JsonProperty("password") val password: String,
)