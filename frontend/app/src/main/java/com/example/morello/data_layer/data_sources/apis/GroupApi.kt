package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.Member
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GroupApi {
    @GET("groups/{id}")
    suspend fun getGroupById(@Path("id") id: Int): Call<Group>

    @PUT("groups/{id}")
    suspend fun updateGroupById(@Path("id") id: Int, @Body group: Group): Call<Group>

    @DELETE("groups/{id}")
    suspend fun deleteGroupById(@Path("id") id: Int): Call<Group>

    @POST("groups")
    suspend fun createGroup(@Body group: Group): Call<Group>
}