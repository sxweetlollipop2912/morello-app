package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.UserApi
import com.example.morello.data_layer.data_sources.apis.models.user_api.LoginRequest
import com.example.morello.data_layer.data_sources.apis.models.user_api.LoginResponse
import com.example.morello.data_layer.data_sources.apis.models.user_api.RefreshTokenRequest
import com.example.morello.data_layer.data_sources.apis.models.user_api.RefreshTokenResponse
import com.example.morello.data_layer.data_sources.apis.models.user_api.RegisterRequest
import com.example.morello.data_layer.data_sources.apis.models.user_api.RegisterResponse
import com.example.morello.data_layer.data_sources.apis.models.user_api.UserMeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class MockedUserApi @Inject constructor() : UserApi {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (loginRequest.email == "user@gmail.com" && loginRequest.password == "password")
            return Response.success(LoginResponse("access-token", "refresh_token"))
        return Response.error(401, ResponseBody.create(null, "Unauthorized"))
    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Response<RefreshTokenResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        return Response.success(RefreshTokenResponse("access-token"))
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        return Response.success(
            RegisterResponse(
                id = 1,
                name = registerRequest.name,
                email = registerRequest.email,
            )
        )
    }

    override suspend fun fetchUserDetail(): Response<UserMeResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        return Response.success(
            UserMeResponse(
                id = 1,
                name = "username",
                email = "user@gmail.com",
                createdAt = "2021-01-01T00:00:00.000Z",
            )
        )
    }
}
