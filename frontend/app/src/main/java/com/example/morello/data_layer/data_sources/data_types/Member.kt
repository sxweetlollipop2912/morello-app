package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class Member(
    @JsonProperty("id") val id: Int,
    @JsonProperty("group") val group: String,
    @JsonProperty("user") val user: String,
    @JsonProperty("name") val name: String,
)
