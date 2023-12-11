package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.Member
import retrofit2.Call
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

    @POST("groups/{id}/members")
    suspend fun addMemberToGroup(
        @Path("id") id: Int,
        @Body member: Member,
    ): Response<Member>

    @DELETE("groups/{id}/members/{memberId}")
    suspend fun deleteMemberFromGroup(
        @Path("id") id: Int,
        @Path("memberId") memberId: Int
    ): Response<Member>

    @PUT("groups/{id}/members/{memberId}")
    suspend fun updateMemberInGroup(
        @Path("id") id: Int,
        @Path("memberId") memberId: Int,
        @Body member: Member,
    ): Response<Member>
}