package com.example.morello.data_layer.data_sources.data_types.groups

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class NewGroupRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
)

data class NewGroupResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("leader_user_id") val leaderUserId: Int,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
)