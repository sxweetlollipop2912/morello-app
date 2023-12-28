package com.example.morello.data_layer.data_sources.data_types.members

import com.example.morello.data_layer.data_sources.data_types.Currency
import com.fasterxml.jackson.annotation.JsonProperty

data class MemberDetails(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("total_due_amount") val totalDueAmount: Currency,
    @JsonProperty("related_sessions") val relatedSessions: List<MemberRelatedSession>,
    @JsonProperty("is_archived") val isArchived: Boolean,
)

data class MemberRelatedSession(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("start") val start: String,
    @JsonProperty("due") val due: String,
    @JsonProperty("is_open") val isOpen: Boolean,
    @JsonProperty("status") val status: Boolean,
    @JsonProperty("payment_per_member") val paymentPerMember: Currency,
)