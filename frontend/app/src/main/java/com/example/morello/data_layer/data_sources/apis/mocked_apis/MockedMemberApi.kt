package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.data_types.Member
import com.example.morello.data_layer.data_sources.data_types.NewMember
import com.example.morello.data_layer.data_sources.data_types.UpdatedMember
import retrofit2.Response
import javax.inject.Inject

class MockedMemberApi @Inject constructor() : MemberApi {
    override suspend fun getMembersByGroupId(id: Int): Response<List<Member>> {
        TODO("Not yet implemented")
    }

    override suspend fun addMemberToGroup(id: Int, member: NewMember): Response<Member> {
        return Response.success(
            Member(
                id = 1,
                name = member.name,
                totalDueAmount = 0,
                relatedSessions = listOf(),
                isArchived = false,
            )
        )
    }

    override suspend fun deleteMemberFromGroup(id: Int, memberId: Int): Response<Member> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMemberInGroup(
        id: Int,
        memberId: Int,
        member: UpdatedMember
    ): Response<Member> {
        TODO("Not yet implemented")
    }
}