package com.example.morello.data_layer.data_types

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class CollectSession(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: LocalDateTime,
    @JsonProperty("due") val due: LocalDateTime,
    @JsonProperty("is_open") val isOpen: Boolean,
    @JsonProperty("payment_per_member") val paymentPerMember: Currency,
    @JsonProperty("paid_count") val paidCount: Int,
    @JsonProperty("member_count") val memberCount: Int,
    @JsonProperty("current_amount") val currentAmount: Currency,
    @JsonProperty("expected_amount") val expectedAmount: Currency,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime,
)

data class MemberStatus(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("status") val status: String
)

data class CollectSessionDetail(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: LocalDateTime,
    @JsonProperty("due") val due: LocalDateTime,
    @JsonProperty("is_open") val isOpen: Boolean,
    @JsonProperty("payment_per_member") val paymentPerMember: Currency,
    @JsonProperty("paid_count") val paidCount: Int,
    @JsonProperty("member_count") val memberCount: Int,
    @JsonProperty("current_amount") val currentAmount: Currency,
    @JsonProperty("expected_amount") val expectedAmount: Currency,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime,
    @JsonProperty("member_statuses") val memberStatuses: List<MemberStatus> // Replace MemberStatus with the actual type
)

data class CollectSessionCreate(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: LocalDateTime,
    @JsonProperty("due") val due: LocalDateTime,
    @JsonProperty("payment_per_member") val paymentPerMember: Currency,
    @JsonProperty("member_ids") val memberIds: List<Int>
)

data class CollectSessionUpdate(
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: LocalDateTime,
    @JsonProperty("due") val due: LocalDateTime
)

data class CollectSessionMemberView(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: LocalDateTime,
    @JsonProperty("due") val due: LocalDateTime,
    @JsonProperty("payment_per_member") val paymentPerMember: Currency,
    @JsonProperty("status") val status: String
)

data class CollectEntryUpdate(
    @JsonProperty("status") val status: String
)
