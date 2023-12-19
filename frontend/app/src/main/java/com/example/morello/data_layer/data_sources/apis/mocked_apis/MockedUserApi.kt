package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.UserApi
import com.example.morello.data_layer.data_sources.data_types.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.RegisterRequest
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class MockedUserApi : UserApi {
    override suspend fun login(loginRequest: LoginRequest): Response<String> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (loginRequest.email == "user@gmail.com" && loginRequest.password == "password")
            return Response.success("token")
        return Response.error(401, ResponseBody.create(null, "Unauthorized"))
    }

    override suspend fun logout(): Response<Nothing> {
        return Response.success(null)
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<String> {
        return Response.success("token")
    }

    override suspend fun fetchUserDetail(userId: Int): Response<User> {
        return Response.success(User(1, "username", "email"))
    }
}
