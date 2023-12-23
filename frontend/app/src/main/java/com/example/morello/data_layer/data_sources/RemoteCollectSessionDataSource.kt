package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.CollectSessionApi
import com.example.morello.data_layer.data_sources.apis.ModeratorApi
import com.example.morello.data_layer.data_sources.apis.client.ErrorResponse
import com.example.morello.data_layer.data_sources.data_types.CollectSession
import com.example.morello.data_layer.data_sources.data_types.CollectSessionEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCollectSessionDataSource @Inject constructor(
    private val moderatorApi: ModeratorApi,
    private val collectSessionApi: CollectSessionApi,
) {
    private val dispatcher = Dispatchers.IO
    suspend fun getCollectSessionEntries(
        groupId: Int,
        collectSessionId: Int
    ): List<CollectSessionEntry> {
        return withContext(dispatcher) {
            val res =
                collectSessionApi.getCollectSessionEntriesBySessionId(groupId, collectSessionId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error getting collect session entries")
            }
        }
    }

    suspend fun updateCollectSessionEntry(
        groupId: Int,
        sessionId: Int,
        entry: CollectSessionEntry
    ) {
        withContext(dispatcher) {
            val res = collectSessionApi.updateCollectSessionEntry(
                groupId,
                sessionId,
                entry.id,
                entry
            )
            if (!res.isSuccessful) {
                throw Exception("Error updating collect session entry")
            }
        }
    }

    suspend fun getCollectSessions(groupId: Int): List<CollectSession> {
        return withContext(dispatcher) {
            val res = collectSessionApi.getCollectSessionsByGroupId(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting collect session")
            }
        }
    }

    suspend fun deleteCollectSession(groupId: Int, collectSessionId: Int) {
        withContext(dispatcher) {
            val res = collectSessionApi.deleteCollectSession(
                groupId,
                collectSessionId
            )
            if (!res.isSuccessful) {
                throw Exception("Error deleting collect session")
            }
        }
    }

    suspend fun updateCollectSession(groupId: Int, collectSession: CollectSession) {
        withContext(dispatcher) {
            val res = collectSessionApi.updateCollectSession(
                groupId,
                collectSession.id,
                collectSession
            )
            if (!res.isSuccessful) {
                throw Exception("Error updating collect session")
            }
        }
    }

    suspend fun createCollectSession(groupId: Int, collectSession: CollectSession) {
        withContext(dispatcher) {
            val res = collectSessionApi.addCollectSessionToGroup(
                groupId,
                collectSession
            )
            if (!res.isSuccessful) {
                throw Exception("Error creating collect session")
            }
        }
    }
}