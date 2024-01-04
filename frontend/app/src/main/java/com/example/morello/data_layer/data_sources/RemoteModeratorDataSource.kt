package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.apis.ModeratorApi
import com.example.morello.data_layer.data_types.Moderator
import com.example.morello.data_layer.data_types.ModeratorCreate
import com.example.morello.data_layer.data_types.ModeratorDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteModeratorDataSource @Inject constructor(
    private val moderatorApi: ModeratorApi
) {
    private val dispatcher = Dispatchers.IO

    suspend fun getModerators(groupId: Int): List<Moderator> {
        return withContext(dispatcher) {
            val res = moderatorApi.getModerators(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting moderators")
            }
        }
    }

    suspend fun getModeratorDetail(groupId: Int, moderatorId: Int): ModeratorDetail {
        return withContext(dispatcher) {
            val res = moderatorApi.getModeratorDetail(groupId, moderatorId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting moderator")
            }
        }
    }

    suspend fun createModerator(groupId: Int, moderator: ModeratorCreate): ModeratorDetail {
        return withContext(dispatcher) {
            val res = moderatorApi.createModerator(groupId, moderator)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error adding moderator")
            }
        }
    }

    suspend fun deleteModerator(groupId: Int, moderatorId: Int) {
        withContext(dispatcher) {
            val res = moderatorApi.deleteModerator(groupId, moderatorId)
            if (!res.isSuccessful) {
                throw Exception("Error deleting moderator")
            }
        }
    }
}