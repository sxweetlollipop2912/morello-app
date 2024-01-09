package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteUserDataSource
import com.example.morello.data_layer.data_sources.SettingDataSource
import com.example.morello.data_layer.data_types.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val settingDataSource: SettingDataSource
) {
    val isLoggedIn: Flow<Boolean> = settingDataSource.getAccessToken().map {
        it.isNotEmpty()
    }

    suspend fun fetchUserDetail(): User {
        return remoteUserDataSource.fetchUserDetail()
    }

    suspend fun login(email: String, password: String) {
        val tokenResponse = remoteUserDataSource.login(email, password)
        settingDataSource.setAccessToken(tokenResponse.access)
        settingDataSource.setRefreshToken(tokenResponse.refresh)
    }

    suspend fun register(username: String, password: String, email: String): User {
        val response = remoteUserDataSource.register(username, password, email)
        return User(
            id = response.id,
            name = response.name,
            email = response.email,
        )
    }

    suspend fun logout() {
        settingDataSource.clearAllTokens()
    }

    suspend fun updateUserDetail(user: User) {
        TODO()
    }
}