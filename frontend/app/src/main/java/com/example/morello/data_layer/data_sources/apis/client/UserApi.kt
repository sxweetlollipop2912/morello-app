package com.example.morello.data_layer.data_sources.apis.client

import com.example.morello.data_layer.data_sources.data_types.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.RegisterRequest
import com.example.morello.data_layer.data_sources.data_types.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface UserApi {
    @GET("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Call<String>

    @GET("users/logout")
    suspend fun logout(): Call<Nothing>

    @GET("users/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Call<String>

    @GET("users/{userId}")
    abstract fun fetchUserDetail(userId: Int): Call<User>
}