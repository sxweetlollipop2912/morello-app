package com.example.morello.data_layer.data_sources

import android.util.Log
import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.client.RetrofitClient
import com.example.morello.data_layer.data_sources.apis.UserApi
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedUserApi
import com.example.morello.data_layer.data_sources.data_types.LoginRequest
import com.example.morello.data_layer.data_sources.data_types.RegisterRequest
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class RemoteUserDataSource(
    private val balanceEntryApi: BalanceEntryApi,
    private val userApi: UserApi,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun login(username: String, password: String): String {
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

    suspend fun logout() {
        withContext(dispatcher) {
            val res = userApi.logout()
            if (!res.isSuccessful) {
                throw Exception("Error logging out")
            }
        }
    }

    suspend fun register(username: String, password: String, email: String): String {
        return withContext(dispatcher) {
            val res = userApi.register(RegisterRequest(username, password, email))
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error registering")
            }
        }
    }

    suspend fun fetchUserDetail(userId: Int): User {
        return withContext(dispatcher) {
            val res = userApi.fetchUserDetail(userId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error fetching user detail")
            }
        }
    }

    companion object Mocked {
        fun createMockedInstance(): RemoteUserDataSource {
            val retrofit = RetrofitClient.getClient()
            val balanceEntryApi = retrofit.create(BalanceEntryApi::class.java)
            val userApi = MockedUserApi()
            val dispatcher: CoroutineDispatcher = Dispatchers.IO
            return RemoteUserDataSource(balanceEntryApi, userApi, dispatcher)
        }
    }
}
