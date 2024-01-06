package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.LoginRequest
import com.example.morello.data_layer.data_types.LoginResponse
import com.example.morello.data_layer.data_types.RefreshTokenRequest
import com.example.morello.data_layer.data_types.RefreshTokenResponse
import com.example.morello.data_layer.data_types.RegisterRequest
import com.example.morello.data_layer.data_types.RegisterResponse
import com.example.morello.data_layer.data_types.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("token/refresh/")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenResponse>

    @POST("users/create/")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @GET("users/me/")
    suspend fun fetchUserDetail(): Response<User>
}