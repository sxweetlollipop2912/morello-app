package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.user.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.user.LoginResponse
import com.example.morello.data_layer.data_sources.data_types.user.RefreshTokenRequest
import com.example.morello.data_layer.data_sources.data_types.user.RefreshTokenResponse
import com.example.morello.data_layer.data_sources.data_types.user.RegisterRequest
import com.example.morello.data_layer.data_sources.data_types.user.RegisterResponse
import com.example.morello.data_layer.data_sources.data_types.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("token/refresh/")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenResponse>

    @POST("users/")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @GET("me/")
    suspend fun fetchUserDetail(): Response<User>
}