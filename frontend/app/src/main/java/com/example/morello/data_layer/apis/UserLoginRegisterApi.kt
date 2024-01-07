package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.LoginRequest
import com.example.morello.data_layer.data_types.LoginResponse
import com.example.morello.data_layer.data_types.RegisterRequest
import com.example.morello.data_layer.data_types.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserLoginRegisterApi {
    @POST("token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("users/create/")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}