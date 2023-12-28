package com.example.morello.data_layer.data_sources.data_types.members

import com.fasterxml.jackson.annotation.JsonProperty

data class NewMemberRequest(
    @JsonProperty("name") val name: String,
)

data class NewMemberResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
)