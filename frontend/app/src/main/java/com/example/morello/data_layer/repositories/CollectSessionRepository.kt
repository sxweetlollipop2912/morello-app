package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSession
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSessionEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectSessionRepository @Inject constructor() {
    fun getCollectSessionEntries(collectSessionId: Int): Flow<List<CollectSessionEntry>> = TODO()
    suspend fun updateCollectSessionEntry(collectSessionEntry: CollectSessionEntry): Nothing =
        TODO()

    fun getCollectSessionById(id: Int): Flow<CollectSession> = TODO()
    suspend fun deleteCollectSession(collectSession: CollectSession): Nothing = TODO()
    suspend fun updateCollectSession(collectSession: CollectSession): Nothing = TODO()
    suspend fun createCollectSession(collectSession: CollectSession): Nothing = TODO()
}