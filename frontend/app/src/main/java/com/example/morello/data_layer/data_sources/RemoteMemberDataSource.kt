package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.data_types.NewMember
import javax.inject.Inject

class RemoteMemberDataSource @Inject constructor(
    private val memberApi: MemberApi
) {
    suspend fun addMemberToGroup(groupId: Int, member: NewMember) {
        memberApi.addMemberToGroup(groupId, member)
    }
}