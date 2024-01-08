package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteModeratorDataSource
import com.example.morello.data_layer.data_types.ModeratorCreate
import javax.inject.Inject

class ModeratorRepository @Inject constructor(
    private val remoteModeratorDataSource: RemoteModeratorDataSource
) {
    suspend fun getModerators(groupId: Int) = remoteModeratorDataSource.getModerators(groupId)

    suspend fun addModerator(groupId: Int, userEmail: String) =
        remoteModeratorDataSource.createModerator(groupId, ModeratorCreate(userEmail))

    suspend fun removeModerator(groupId: Int, moderatorId: Int) =
        remoteModeratorDataSource.deleteModerator(groupId, moderatorId)
}