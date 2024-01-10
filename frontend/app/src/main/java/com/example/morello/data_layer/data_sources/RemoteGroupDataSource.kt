package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.apis.GroupApi
import com.example.morello.data_layer.data_types.Group
import com.example.morello.data_layer.data_types.GroupCreate
import com.example.morello.data_layer.data_types.GroupDetail
import com.example.morello.data_layer.data_types.GroupUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteGroupDataSource @Inject constructor(
    private val groupApi: GroupApi,
) {
    private val dispatcher = Dispatchers.IO

    suspend fun getGroups(): List<Group> {
        return withContext(dispatcher) {
            val res = groupApi.getGroups()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting groups")
            }
        }
    }

    suspend fun getGroup(id: Int): GroupDetail {
        return withContext(dispatcher) {
            val res = groupApi.getGroupDetail(id)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting group")
            }
        }
    }

    suspend fun createGroup(group: GroupCreate): GroupDetail {
        return withContext(dispatcher) {
            val res = groupApi.createGroup(group)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error creating group")
            }
        }
    }

    suspend fun updateGroup(groupId: Int, group: GroupUpdate): GroupDetail {
        return withContext(dispatcher) {
            val res = groupApi.updateGroup(groupId, group)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error updating group")
            }
        }
    }

    suspend fun deleteGroup(groupId: Int): Void {
        return withContext(dispatcher) {
            val res = groupApi.deleteGroup(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error deleting group")
            }
        }
    }
}