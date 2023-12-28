package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.data_types.members.Member
import com.example.morello.data_layer.data_sources.data_types.members.MemberDetails
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberRequest
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberResponse
import com.example.morello.data_layer.data_sources.data_types.members.UpdateMemberRequest
import com.example.morello.data_layer.data_sources.data_types.members.UpdateMemberResponse
import retrofit2.Response
import javax.inject.Inject

class MockedMemberApi @Inject constructor() : MemberApi {
    override suspend fun getMembersByGroupId(id: Int): Response<List<Member>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMemberDetails(id: Int, memberId: Int): Response<MemberDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun addMemberToGroup(id: Int, member: NewMemberRequest): Response<NewMemberResponse> {
        return Response.success(
            NewMemberResponse(
                id = 1,
                name = member.name
            )
        )
    }

    override suspend fun deleteMemberFromGroup(id: Int, memberId: Int): Response<Member> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMemberInGroup(
        id: Int,
        memberId: Int,
        member: UpdateMemberRequest
    ): Response<UpdateMemberResponse> {
        TODO("Not yet implemented")
    }
}