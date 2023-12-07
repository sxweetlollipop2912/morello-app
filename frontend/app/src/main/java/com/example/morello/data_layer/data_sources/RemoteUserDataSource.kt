package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.apis.client.RetrofitClient
import com.example.morello.data_layer.data_sources.apis.client.UserApi
import com.example.morello.data_layer.data_sources.data_types.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.RegisterRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteUserDataSource {
    private val retrofit = RetrofitClient.getClient()
    private val userApi = retrofit.create(UserApi::class.java)
    private val balanceEntryApi = retrofit.create(BalanceEntryApi::class.java)
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun login(username: String, password: String): String {
        return withContext(ioDispatcher) {
            val res = userApi.login(LoginRequest(username, password)).execute()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error logging in")
            }
        }
    }

    suspend fun logout() {
        withContext(ioDispatcher) {
            val res = userApi.logout().execute()
            if (!res.isSuccessful) {
                throw Exception("Error logging out")
            }
        }
    }

    suspend fun register(username: String, password: String, email: String): String {
        return withContext(ioDispatcher) {
            val res = userApi.register(RegisterRequest(username, password, email)).execute()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error registering")
            }
        }
    }
}