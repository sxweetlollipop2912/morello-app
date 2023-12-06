package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.flow.Flow

class UserRepository {
    fun getUserById(id: Int): Flow<User> = TODO()
    suspend fun updateUserDetail(user: User): Nothing = TODO()
    fun getLeadedGroups(userId: Int): Flow<List<Int>> = TODO()
    fun getModeratedGroups(userId: Int): Flow<List<Int>> = TODO()
}