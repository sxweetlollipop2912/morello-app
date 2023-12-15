package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.data_types.Member
import retrofit2.Response

class MockedMemberApi : MemberApi {
    override suspend fun getMembersByGroupId(id: Int): Response<List<Member>> {
        TODO("Not yet implemented")
    }

    override suspend fun addMemberToGroup(id: Int, member: Member): Response<Member> {
        return Response.success(member)
    }

    override suspend fun deleteMemberFromGroup(id: Int, memberId: Int): Response<Member> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMemberInGroup(
        id: Int,
        memberId: Int,
        member: Member
    ): Response<Member> {
        TODO("Not yet implemented")
    }
}