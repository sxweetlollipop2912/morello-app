package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteCollectSessionDataSource
import com.example.morello.data_layer.data_types.CollectEntryUpdate
import com.example.morello.data_layer.data_types.CollectSessionCreate
import javax.inject.Inject

class CollectSessionRepository @Inject constructor(
    private val remoteCollectSessionDataSource: RemoteCollectSessionDataSource,
) {
    suspend fun getCollectSessions(groupId: Int) =
        remoteCollectSessionDataSource.getCollectSessions(groupId)

    suspend fun getCollectionSessionDetail(groupId: Int, sessionId: Int) =
        remoteCollectSessionDataSource.getCollectSessionDetail(groupId, sessionId)

    suspend fun createCollectSession(groupId: Int, collectSession: CollectSessionCreate) {
        remoteCollectSessionDataSource.createCollectSession(groupId, collectSession)
    }

    suspend fun updateCollectEntryStatus(
        groupId: Int,
        sessionId: Int,
        entryId: Int,
        status: Boolean
    ) {
        remoteCollectSessionDataSource.updateCollectEntry(
            groupId,
            sessionId,
            entryId,
            CollectEntryUpdate(status)
        )
    }

    suspend fun closeCollectSession(groupId: Int, sessionId: Int) =
        remoteCollectSessionDataSource.closeCollectSession(groupId, sessionId)
}