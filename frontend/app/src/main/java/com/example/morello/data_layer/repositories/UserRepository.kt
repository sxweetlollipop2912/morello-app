package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteUserDataSource
import com.example.morello.data_layer.data_sources.SettingDataSource
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.User
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
        val userResponse = remoteUserDataSource.fetchUserDetail()
        return User(
            id = userResponse.id,
            name = userResponse.name,
            email = userResponse.email
        )
    }

    suspend fun login(email: String, password: String) {
        val tokenResponse = remoteUserDataSource.login(email, password)
        settingDataSource.setAccessToken(tokenResponse.access)
        settingDataSource.setRefreshToken(tokenResponse.refresh)
    }

    suspend fun register(username: String, password: String, email: String): User {
        val userResponse = remoteUserDataSource.register(username, password, email)
        return User(
            id = userResponse.id,
            name = userResponse.name,
            email = userResponse.email
        )
    }

    suspend fun logout() {
        settingDataSource.setAccessToken("")
        settingDataSource.setRefreshToken("")
    }

    suspend fun updateUserDetail(user: User) {
        TODO()
    }

    suspend fun getManagedGroups(userId: Int): List<Group> {
        return emptyList()
    }

    suspend fun getLeadedGroups(userId: Int): List<Group> {
        return emptyList()
    }

    suspend fun getModeratedGroups(userId: Int): List<Group> {
        return emptyList()
    }
}