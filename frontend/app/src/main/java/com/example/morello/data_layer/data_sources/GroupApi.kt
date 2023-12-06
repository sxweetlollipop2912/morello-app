package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.data_types.Group
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupApi {
    @GET("groups/{id}")
    suspend fun getGroupById(@Path("id") id: Int): Call<Group>
}