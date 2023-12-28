package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.members.Member
import com.example.morello.data_layer.data_sources.data_types.members.MemberDetails
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberRequest
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberResponse
import com.example.morello.data_layer.data_sources.data_types.members.UpdateMemberRequest
import com.example.morello.data_layer.data_sources.data_types.members.UpdateMemberResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberApi {
    @GET("groups/{id}/members")
    suspend fun getMembersByGroupId(@Path("id") id: Int): Response<List<Member>>

    @GET("groups/{id}/members/{memberId}")
    suspend fun getMemberDetails(
        @Path("id") id: Int,
        @Path("memberId") memberId: Int
    ): Response<MemberDetails>

    @POST("groups/{id}/members")
    suspend fun addMemberToGroup(
        @Path("id") id: Int,
        @Body member: NewMemberRequest,
    ): Response<NewMemberResponse>

    @DELETE("groups/{id}/members/{memberId}")
    suspend fun deleteMemberFromGroup(
        @Path("id") id: Int,
        @Path("memberId") memberId: Int
    ): Response<Member>

    @PUT("groups/{id}/members/{memberId}")
    suspend fun updateMemberInGroup(
        @Path("id") id: Int,
        @Path("memberId") memberId: Int,
        @Body member: UpdateMemberRequest,
    ): Response<UpdateMemberResponse>
}