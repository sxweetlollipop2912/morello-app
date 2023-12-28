package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.groups.Group
import com.example.morello.data_layer.data_sources.data_types.groups.GroupDetails
import com.example.morello.data_layer.data_sources.data_types.groups.Leader
import com.example.morello.data_layer.data_sources.data_types.groups.NewGroupRequest
import com.example.morello.data_layer.data_sources.data_types.groups.NewGroupResponse
import com.example.morello.data_layer.data_sources.data_types.groups.UpdateGroupRequest
import com.example.morello.data_layer.data_sources.data_types.groups.UpdateGroupResponse
import com.example.morello.data_layer.data_sources.data_types.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GroupApi {
    @GET("groups/")
    suspend fun getManagedGroups(): Response<List<Group>>

    @GET("groups/{id}")
    suspend fun getGroupById(@Path("id") id: Int): Response<GroupDetails>

    @GET("groups/{id}/leader")
    suspend fun getLeaderByGroupId(@Path("id") id: Int): Response<Leader>

    @PUT("groups/{id}")
    suspend fun updateGroupById(@Path("id") id: Int, @Body group: UpdateGroupRequest): Response<UpdateGroupResponse>

    @DELETE("groups/{id}")
    suspend fun deleteGroupById(@Path("id") id: Int): Response<Group>

    @POST("groups")
    suspend fun createGroup(@Body group: NewGroupRequest): Response<NewGroupResponse>
}