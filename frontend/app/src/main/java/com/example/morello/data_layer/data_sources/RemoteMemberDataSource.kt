package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.data_types.Member

class RemoteMemberDataSource(
    private val memberApi: MemberApi
) {
    suspend fun addMemberToGroup(groupId: Int, member: Member) {
        memberApi.addMemberToGroup(groupId, member)
    }
}