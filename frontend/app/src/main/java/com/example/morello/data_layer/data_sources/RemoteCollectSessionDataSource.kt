package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.apis.CollectSessionApi
import com.example.morello.data_layer.data_types.CollectEntryUpdate
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.data_types.CollectSessionCreate
import com.example.morello.data_layer.data_types.CollectSessionDetail
import com.example.morello.data_layer.data_types.CollectSessionUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCollectSessionDataSource @Inject constructor(
    private val collectSessionApi: CollectSessionApi,
) {
    private val dispatcher = Dispatchers.IO

    suspend fun getCollectSessions(groupId: Int): List<CollectSession> {
        return withContext(dispatcher) {
            val res = collectSessionApi.getCollectSessions(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting collect sessions")
            }
        }
    }

    suspend fun getCollectSessionDetail(groupId: Int, sessionId: Int): CollectSessionDetail {
        return withContext(dispatcher) {
            val res = collectSessionApi.getCollectSessionDetail(
                groupId,
                sessionId
            )
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting collect session")
            }
        }
    }

    suspend fun createCollectSession(groupId: Int, collectSession: CollectSessionCreate) {
        withContext(dispatcher) {
            val res = collectSessionApi.createCollectSession(
                groupId,
                collectSession
            )
            if (!res.isSuccessful) {
                throw Exception("Error creating collect session")
            }
        }
    }

    suspend fun updateCollectSession(
        groupId: Int,
        sessionId: Int,
        collectSession: CollectSessionUpdate
    ) {
        withContext(dispatcher) {
            val res = collectSessionApi.updateCollectSession(
                groupId,
                sessionId,
                collectSession
            )
            if (!res.isSuccessful) {
                throw Exception("Error updating collect session")
            }
        }
    }

    suspend fun closeCollectSession(groupId: Int, sessionId: Int) {
        withContext(dispatcher) {
            val res = collectSessionApi.closeCollectSession(
                groupId,
                sessionId
            )
            if (!res.isSuccessful) {
                throw Exception("Error closing collect session")
            }
        }
    }

    suspend fun deleteCollectSession(groupId: Int, sessionId: Int) {
        withContext(dispatcher) {
            val res = collectSessionApi.deleteCollectSession(
                groupId,
                sessionId
            )
            if (!res.isSuccessful) {
                throw Exception("Error deleting collect session")
            }
        }
    }

    suspend fun updateCollectEntry(
        groupId: Int,
        sessionId: Int,
        entryId: Int,
        collectEntry: CollectEntryUpdate
    ) {
        withContext(dispatcher) {
            val res = collectSessionApi.updateCollectEntry(
                groupId,
                sessionId,
                entryId,
                collectEntry
            )
            if (!res.isSuccessful) {
                throw Exception("Error updating collect session entry status")
            }
        }
    }
}