package com.example.morello.data_layer.repositories

import android.util.Log
import com.example.morello.data_layer.data_sources.RemoteUserDataSource
import com.example.morello.data_layer.data_sources.SettingDataSource
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val settingDataSource: SettingDataSource
) {
    val isLoggedIn: Flow<Boolean> = settingDataSource.getToken().map {
        it.isNotEmpty()
    }

    suspend fun fetchUserDetail(userId: Int): User {
        return remoteUserDataSource.fetchUserDetail(userId)
    }

    suspend fun login(email: String, password: String) {
        val token = remoteUserDataSource.login(email, password)
        settingDataSource.setToken(token)
    }

    suspend fun register(username: String, password: String, email: String) {
        val token = remoteUserDataSource.register(username, password, email)
        settingDataSource.setToken(token)
    }

    suspend fun logout() {
        settingDataSource.setToken("")
    }

    suspend fun updateUserDetail(user: User) {
        TODO()
    }

    fun getLeadedGroups(userId: Int): Flow<List<Int>> = TODO()
    fun getModeratedGroups(userId: Int): Flow<List<Int>> = TODO()
}