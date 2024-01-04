package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.GroupDetail
import com.example.morello.data_layer.data_types.Group
import com.example.morello.data_layer.data_types.GroupCreate
import com.example.morello.data_layer.data_types.GroupUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GroupApi {
    @GET("groups/")
    suspend fun getGroups(): Response<List<Group>>

    @GET("groups/{groupId}/")
    suspend fun getGroupDetail(@Path("groupId") groupId: Int): Response<GroupDetail>

    @POST("groups/")
    suspend fun createGroup(
        @Body group: GroupCreate
    ): Response<GroupDetail>

    @PUT("groups/{groupId}/")
    suspend fun updateGroupById(
        @Path("groupId") groupId: Int,
        @Body group: GroupUpdate
    ): Response<GroupDetail>

    @DELETE("groups/{groupId}/")
    suspend fun deleteGroupById(
        @Path("groupId") groupId: Int
    ): Response<Void>
}