package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ModeratorApi {
    @GET("groups/{id}/moderators")
    suspend fun getModeratorsByGroupId(@Path("id") id: Int): Call<List<User>>

    @POST("groups/{id}/moderators")
    suspend fun addModeratorToGroup(@Body moderator: User): Call<User>

    @DELETE("groups/{id}/moderators/{moderatorId}")
    suspend fun deleteModeratorFromGroup(
        @Path("id") groupId: Int,
        @Path("moderatorId") moderatorId: Int
    ): Call<User>
}