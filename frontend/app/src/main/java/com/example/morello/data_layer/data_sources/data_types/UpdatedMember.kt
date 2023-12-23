package com.example.morello.data_layer.data_sources.data_types

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdatedMember(
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean,
)
