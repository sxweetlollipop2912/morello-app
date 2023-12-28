package com.example.morello.data_layer.data_sources

import android.util.Log
import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.UserApi
import com.example.morello.data_layer.data_sources.data_types.user.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.user.LoginResponse
import com.example.morello.data_layer.data_sources.data_types.user.RegisterRequest
import com.example.morello.data_layer.data_sources.data_types.user.RegisterResponse
import com.example.morello.data_layer.data_sources.data_types.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val balanceEntryApi: BalanceEntryApi,
    private val userApi: UserApi,
) {
    private val dispatcher = Dispatchers.IO

    suspend fun login(username: String, password: String): LoginResponse {
        return withContext(dispatcher) {
            val res = userApi.login(LoginRequest(username, password))
            Log.d("RemoteUserDataSource", res.toString())
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception(res.errorBody().toString())
            }
        }
    }

    suspend fun register(username: String, password: String, email: String): RegisterResponse {
        return withContext(dispatcher) {
            val res = userApi.register(RegisterRequest(username, password, email))
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
