package com.example.morello.data_layer.data_sources.data_types.members

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateMemberRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean,
)

data class UpdateMemberResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean,
)