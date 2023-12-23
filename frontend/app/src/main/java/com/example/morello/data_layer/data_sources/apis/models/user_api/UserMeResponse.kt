package com.example.morello.data_layer.data_sources.apis.models.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class UserMeResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("created_at") val createdAt: String,
)