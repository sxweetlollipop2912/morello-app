package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.Moderator
import com.example.morello.data_layer.data_types.ModeratorCreate
import com.example.morello.data_layer.data_types.ModeratorDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ModeratorApi {
    @GET("groups/{groupId}/moderators/")
    suspend fun getModerators(@Path("groupId") groupId: Int): Response<List<Moderator>>

    @GET("groups/{groupId}/moderators/{moderatorId}/")
    suspend fun getModeratorDetail(
        @Path("groupId") groupId: Int,
        @Path("moderatorId") moderatorId: Int
    ): Response<ModeratorDetail>

    @POST("groups/{groupId}/moderators/")
    suspend fun createModerator(
        @Path("groupId") groupId: Int,
        @Body moderator: ModeratorCreate
    ): Response<ModeratorDetail>

    @DELETE("groups/{groupId}/moderators/{moderatorId}/")
    suspend fun deleteModerator(
        @Path("groupId") groupId: Int,
        @Path("moderatorId") moderatorId: Int
    ): Response<Void>
}