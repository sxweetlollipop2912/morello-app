package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.data_types.Member
import com.example.morello.data_layer.data_sources.data_types.NewMember

class RemoteMemberDataSource(
    private val memberApi: MemberApi
) {
    suspend fun addMemberToGroup(groupId: Int, member: NewMember) {
        memberApi.addMemberToGroup(groupId, member)
    }
}