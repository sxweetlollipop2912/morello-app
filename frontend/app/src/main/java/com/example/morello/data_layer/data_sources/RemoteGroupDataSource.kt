package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.apis.client.ErrorResponse
import com.example.morello.data_layer.data_sources.apis.client.RetrofitClient
import com.example.morello.data_layer.data_sources.data_types.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.CollectSession
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.Member
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteGroupDataSource {
    private val retrofit = RetrofitClient.getClient()
    private val groupApi = retrofit.create(GroupApi::class.java)
    private val memberApi = retrofit.create(MemberApi::class.java)
    private val balanceEntryApi = retrofit.create(BalanceEntryApi::class.java)
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getGroupById(id: Int): Group {
        return withContext(ioDispatcher) {
            val res = groupApi.getGroupById(id).execute()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting group")
            }
        }
    }

    suspend fun getLeader(groupId: Int): User {
        return withContext(ioDispatcher) {
            val res = groupApi.getLeaderByGroupId(groupId).execute()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting leader")
            }
        }
    }

    suspend fun deleteGroup(groupId: Int) {
        withContext(ioDispatcher) {
            val res = groupApi.deleteGroupById(groupId).execute()
            if (!res.isSuccessful) {
                throw Exception("Error deleting group")
            }
        }
    }

    suspend fun createNewGroup(newGroup: Group) {
        withContext(ioDispatcher) {
            val res = groupApi.createGroup(newGroup).execute()
            if (!res.isSuccessful) {
                throw Exception("Error creating group")
            }
        }
    }

    suspend fun updateGroup(updatedGroup: Group) {
        withContext(ioDispatcher) {
            val res = groupApi.updateGroupById(updatedGroup.id, updatedGroup).execute()
            if (!res.isSuccessful) {
                throw Exception("Error updating group")
            }
        }
    }

    suspend fun getModerators(groupId: Int): List<User> = TODO()
    suspend fun getModeratorCorrespondingToMember(groupId: Int, memberId: Int): User = TODO()
    suspend fun addModerator(groupId: Int, moderatorId: Int): Nothing = TODO()
    suspend fun removeModerator(groupId: Int, moderatorId: Int): Nothing = TODO()

    suspend fun getCollectSessions(groupId: Int): List<CollectSession> = TODO()

    suspend fun getMembers(groupId: Int): List<Member> {
        return withContext(ioDispatcher) {
            val res = memberApi.getMembersByGroupId(groupId).execute()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error getting members: ${err}}")
            }
        }
    }

    suspend fun removeMember(groupId: Int, memberId: Int) {
        withContext(ioDispatcher) {
            val res = memberApi.deleteMemberFromGroup(groupId, memberId).execute()
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error removing member: ${err}}")
            }
        }
    }

    suspend fun addMember(groupId: Int, member: Member) {
        withContext(ioDispatcher) {
            val res = memberApi.addMemberToGroup(groupId, member).execute()
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error adding member: ${err}}")
            }
        }
    }

    suspend fun getBalanceEntries(groupId: Int): List<BalanceEntry> {
        return withContext(ioDispatcher) {
            val res = balanceEntryApi.getBalanceEntriesByGroupId(groupId).execute()
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error getting balance entries: ${err}}")
            }
        }
    }

    suspend fun deleteBalanceEntry(groupId: Int, balanceEntryId: Int) {
        withContext(ioDispatcher) {
            val res = balanceEntryApi.deleteBalanceEntryFromGroup(groupId, balanceEntryId).execute()
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error deleting balance entry: ${err}}")
            }
        }
    }

    suspend fun updateBalanceEntry(groupId: Int, balanceEntry: BalanceEntry) {
        withContext(ioDispatcher) {
            val res =
                balanceEntryApi.updateBalanceEntryInGroup(groupId, balanceEntry.id, balanceEntry)
                    .execute()
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error updating balance entry: ${err}}")
            }
        }
    }

    suspend fun createBalanceEntry(groupId: Int, balanceEntry: BalanceEntry) {
        withContext(ioDispatcher) {
            val res = balanceEntryApi.addBalanceEntryToGroup(groupId, balanceEntry).execute()
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error creating balance entry: ${err}}")
            }
        }
    }
}