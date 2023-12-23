package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class NewMember(
    @JsonProperty("name") val name: String,
)