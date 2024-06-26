package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.apis.BalanceApi
import com.example.morello.data_layer.apis.UserApi
import com.example.morello.data_layer.apis.UserLoginRegisterApi
import com.example.morello.data_layer.data_types.LoginRequest
import com.example.morello.data_layer.data_types.LoginResponse
import com.example.morello.data_layer.data_types.RegisterRequest
import com.example.morello.data_layer.data_types.RegisterResponse
import com.example.morello.data_layer.data_types.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WrongUsernameOrPasswordException : Exception("Wrong username or password")

class RemoteUserDataSource @Inject constructor(
    private val balanceApi: BalanceApi,
    private val userApi: UserApi,
    private val userLoginRegisterApi: UserLoginRegisterApi,
) {
    private val dispatcher = Dispatchers.IO

    suspend fun login(username: String, password: String): LoginResponse {
        return withContext(dispatcher) {
            val res = userLoginRegisterApi.login(LoginRequest(username, password))
            if (res.code() == 401) {
                throw WrongUsernameOrPasswordException()
            }
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Fail to connect to server")
            }
        }
    }

    suspend fun register(username: String, password: String, email: String): RegisterResponse {
        return withContext(dispatcher) {
            val res = userLoginRegisterApi.register(RegisterRequest(username, password, email))
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error registering")
            }
        }
    }

    suspend fun fetchUserDetail(): User {
        return withContext(dispatcher) {
            val res = userApi.fetchUserDetail()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error fetching user detail")
            }
        }
    }
}
