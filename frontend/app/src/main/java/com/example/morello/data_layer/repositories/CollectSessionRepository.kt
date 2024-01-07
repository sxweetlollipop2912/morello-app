package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteCollectSessionDataSource
import com.example.morello.data_layer.data_types.CollectSessionCreate
import javax.inject.Inject

class CollectSessionRepository @Inject constructor(
    private val remoteCollectSessionDataSource: RemoteCollectSessionDataSource,
) {
    suspend fun getCollectSessions(groupId: Int) =
        remoteCollectSessionDataSource.getCollectSessions(groupId)

    suspend fun createCollectSession(groupId: Int, collectSession: CollectSessionCreate) {
        remoteCollectSessionDataSource.createCollectSession(groupId, collectSession)
    }
//    fun getCollectSessionEntries(collectSessionId: Int): Flow<List<CollectSessionEntry>> = TODO()
//    suspend fun updateCollectSessionEntry(collectSessionEntry: CollectSessionEntry): Nothing =
//        TODO()
//
//    fun getCollectSessionById(id: Int): Flow<CollectSession> = TODO()
//    suspend fun deleteCollectSession(collectSession: CollectSession): Nothing = TODO()
//    suspend fun updateCollectSession(collectSession: CollectSession): Nothing = TODO()
//    suspend fun createCollectSession(collectSession: CollectSession): Nothing = TODO()
}