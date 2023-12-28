package com.example.morello.data_layer.data_sources.data_types.groups

import com.fasterxml.jackson.annotation.JsonProperty

data class Leader (
    @JsonProperty("id") val id: Int,
    @JsonProperty("email") val email: String,
    @JsonProperty("name") val name: String,
)