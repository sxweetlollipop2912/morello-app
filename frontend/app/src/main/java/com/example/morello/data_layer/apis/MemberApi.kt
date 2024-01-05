package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.Member
import com.example.morello.data_layer.data_types.MemberCreate
import com.example.morello.data_layer.data_types.MemberDetail
import com.example.morello.data_layer.data_types.MemberUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberApi {
    @GET("groups/{groupId}/members/")
    suspend fun getMembers(
        @Path("groupId") groupId: Int
    ): Response<List<Member>>

    @GET("groups/{groupId}/members/{memberId}/")
    suspend fun getMemberDetail(
        @Path("groupId") groupId: Int,
        @Path("memberId") memberId: Int
    ): Response<MemberDetail>

    @POST("groups/{groupId}/members/")
    suspend fun createMember(
        @Path("groupId") groupId: Int,
        @Body member: MemberCreate
    ): Response<MemberDetail>

    @PUT("groups/{groupId}/members/{memberId}/")
    suspend fun updateMember(
        @Path("groupId") groupId: Int,
        @Path("memberId") memberId: Int,
        @Body member: MemberUpdate
    ): Response<MemberDetail>

    @DELETE("groups/{groupId}/members/{memberId}/")
    suspend fun deleteMember(
        @Path("groupId") groupId: Int,
        @Path("memberId") memberId: Int
    ): Response<Void>
}