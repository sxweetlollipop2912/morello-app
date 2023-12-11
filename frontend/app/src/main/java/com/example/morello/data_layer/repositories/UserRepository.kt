package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteUserDataSource
import com.example.morello.data_layer.data_sources.SettingDataStore
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val settingDataStore: SettingDataStore
) {
    val isLoggedIn: Flow<Boolean> = settingDataStore.getToken().map {
        it.isNotEmpty()
    }

    suspend fun fetchUserDetail(userId: Int): User {
        return remoteUserDataSource.fetchUserDetail(userId)
    }

    suspend fun login(username: String, password: String) {
        val token = remoteUserDataSource.login(username, password)
        settingDataStore.setToken(token)
    }

    suspend fun register(username: String, password: String, email: String) {
        val token = remoteUserDataSource.register(username, password, email)
        settingDataStore.setToken(token)
    }

    suspend fun logout() {
        settingDataStore.setToken("")
    }

    suspend fun updateUserDetail(user: User) {
        TODO()
    }

    fun getLeadedGroups(userId: Int): Flow<List<Int>> = TODO()
    fun getModeratedGroups(userId: Int): Flow<List<Int>> = TODO()
}