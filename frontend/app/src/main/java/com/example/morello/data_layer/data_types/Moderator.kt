package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Moderator(
    @JsonProperty("id") val id: Int,
    @JsonProperty("user_id") val userId: Int,
    @JsonProperty("user_email") val userEmail: String,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime
)

data class ModeratorDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("user_id") val userId: Int,
    @JsonProperty("user_email") val userEmail: String,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime
)

data class ModeratorCreate(
    @JsonProperty("user_email") val userEmail: String,
)