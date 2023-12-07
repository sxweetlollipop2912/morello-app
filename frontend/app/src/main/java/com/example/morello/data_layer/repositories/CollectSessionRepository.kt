package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.data_types.CollectSession
import com.example.morello.data_layer.data_sources.data_types.CollectSessionEntry
import kotlinx.coroutines.flow.Flow

class CollectSessionRepository {
    fun getCollectSessionEntries(collectSessionId: Int): Flow<List<CollectSessionEntry>> = TODO()
    suspend fun updateCollectSessionEntry(collectSessionEntry: CollectSessionEntry): Nothing =
        TODO()

    fun getCollectSessionById(id: Int): Flow<CollectSession> = TODO()
    suspend fun deleteCollectSession(collectSession: CollectSession): Nothing = TODO()
    suspend fun updateCollectSession(collectSession: CollectSession): Nothing = TODO()
    suspend fun createCollectSession(collectSession: CollectSession): Nothing = TODO()
}