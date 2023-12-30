package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ModeratorApi {
    @GET("groups/{id}/moderators/")
    suspend fun getModeratorsByGroupId(@Path("id") id: Int): Response<List<User>>

    @POST("groups/{id}/moderators/")
    suspend fun addModeratorToGroup(@Body moderator: User): Response<User>

    @DELETE("groups/{id}/moderators/{moderatorId}/")
    suspend fun deleteModeratorFromGroup(
        @Path("id") groupId: Int,
        @Path("moderatorId") moderatorId: Int
    ): Response<User>
}