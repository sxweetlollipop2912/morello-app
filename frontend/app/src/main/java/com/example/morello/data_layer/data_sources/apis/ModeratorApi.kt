package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ModeratorApi {
    @GET("groups/{id}/moderators")
    suspend fun getModeratorsByGroupId(id: Int): Call<List<User>>

    @POST("groups/{id}/moderators")
    suspend fun addModeratorToGroup(@Body moderator: User): Call<User>

    @DELETE("groups/{id}/moderators/{moderatorId}")
    suspend fun deleteModeratorFromGroup(id: Int, moderatorId: Int): Call<User>
}