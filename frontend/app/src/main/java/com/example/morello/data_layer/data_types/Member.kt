package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class Member(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime
)

data class MemberCreate(
    @JsonProperty("name") val name: String,
)

data class MemberUpdate(
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean
)

data class MemberDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("is_archived") val isArchived: Boolean,
    @JsonProperty("created_at") val createdAt: OffsetDateTime,
    @JsonProperty("updated_at") val updatedAt: OffsetDateTime,
    @JsonProperty("total_due_amount") val dueAmount: Currency,
    @JsonProperty("related_sessions") val relatedSessions: List<CollectSessionMemberView>
)