package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteCollectSessionDataSource
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSession
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSessionEntry
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.NewCollectSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectSessionRepository @Inject constructor(
    private val remoteCollectSessionDataSource: RemoteCollectSessionDataSource
) {
    suspend fun getCollectSessionEntries(collectSessionId: Int): List<CollectSessionEntry> = TODO()
    suspend fun updateCollectSessionEntry(collectSessionEntry: CollectSessionEntry): Nothing =
        TODO()

    fun getCollectSessionById(id: Int): Flow<CollectSession> = TODO()
    suspend fun deleteCollectSession(collectSession: CollectSession): Nothing = TODO()
    suspend fun updateCollectSession(collectSession: CollectSession): Nothing = TODO()
    suspend fun createCollectSession(groupId: Int, collectSession: NewCollectSession) {
        if (collectSession.name.isBlank()) {
            throw Exception("Collect session name cannot be blank")
        } else if (collectSession.name == "error") {
            throw Exception("Error creating collect session")
        }
    }
}