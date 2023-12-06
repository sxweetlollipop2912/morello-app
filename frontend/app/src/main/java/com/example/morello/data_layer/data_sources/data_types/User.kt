package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
)
