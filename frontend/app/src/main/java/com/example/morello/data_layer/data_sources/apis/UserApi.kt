package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.RegisterRequest
import com.example.morello.data_layer.data_sources.data_types.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface UserApi {
    @GET("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<String>

    @GET("users/logout")
    suspend fun logout(): Response<Nothing>

    @GET("users/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<String>

    @GET("users/{userId}")
    suspend fun fetchUserDetail(userId: Int): Response<User>
}