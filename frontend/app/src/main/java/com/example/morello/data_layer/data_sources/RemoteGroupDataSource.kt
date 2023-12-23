package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.apis.client.ErrorResponse
import com.example.morello.data_layer.data_sources.apis.client.RetrofitClient
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedBalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedGroupApi
import com.example.morello.data_layer.data_sources.apis.mocked_apis.MockedMemberApi
import com.example.morello.data_layer.data_sources.data_types.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.CollectSession
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.Member
import com.example.morello.data_layer.data_sources.data_types.NewGroup
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteGroupDataSource(
    private val groupApi: GroupApi,
    private val memberApi: MemberApi,
    private val balanceEntryApi: BalanceEntryApi,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getGroupById(id: Int): Group {
        return withContext(dispatcher) {
            val res = groupApi.getGroupById(id)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting group")
            }
        }
    }

    suspend fun getLeader(groupId: Int): User {
        return withContext(dispatcher) {
            val res = groupApi.getLeaderByGroupId(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting leader")
            }
        }
    }

    suspend fun deleteGroup(groupId: Int) {
        withContext(dispatcher) {
            val res = groupApi.deleteGroupById(groupId)
            if (!res.isSuccessful) {
                throw Exception("Error deleting group")
            }
        }
    }

    suspend fun createNewGroup(newGroup: NewGroup) {
        withContext(dispatcher) {
            val res = groupApi.createGroup(newGroup)
            if (!res.isSuccessful) {
                throw Exception("Error creating group")
            }
        }
    }

    suspend fun updateGroup(updatedGroup: Group) {
        withContext(dispatcher) {
            val res = groupApi.updateGroupById(updatedGroup.id, updatedGroup)
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
        return withContext(dispatcher) {
            val res = memberApi.getMembersByGroupId(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error getting members: ${err}}")
            }
        }
    }

    suspend fun removeMember(groupId: Int, memberId: Int) {
        withContext(dispatcher) {
            val res = memberApi.deleteMemberFromGroup(groupId, memberId)
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error removing member: ${err}}")
            }
        }
    }

    suspend fun addMember(groupId: Int, member: Member) {
        withContext(dispatcher) {
            val res = memberApi.addMemberToGroup(groupId, member)
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error adding member: ${err}}")
            }
        }
    }

    suspend fun getBalanceEntries(groupId: Int): List<BalanceEntry> {
        return withContext(dispatcher) {
            val res = balanceEntryApi.getBalanceEntriesByGroupId(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error getting balance entries: ${err}}")
            }
        }
    }

    suspend fun deleteBalanceEntry(groupId: Int, balanceEntryId: Int) {
        withContext(dispatcher) {
            val res = balanceEntryApi.deleteBalanceEntryFromGroup(groupId, balanceEntryId)
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error deleting balance entry: ${err}}")
            }
        }
    }

    suspend fun updateBalanceEntry(groupId: Int, balanceEntry: BalanceEntry) {
        withContext(dispatcher) {
            val res =
                balanceEntryApi.updateBalanceEntryInGroup(groupId, balanceEntry.id, balanceEntry)
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error updating balance entry: ${err}}")
            }
        }
    }

    suspend fun createBalanceEntry(groupId: Int, balanceEntry: BalanceEntry) {
        withContext(dispatcher) {
            val res = balanceEntryApi.addBalanceEntryToGroup(groupId, balanceEntry)
            if (!res.isSuccessful) {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error creating balance entry: ${err}}")
            }
        }
    }

    companion object Mocked {
        fun createMockedInstance(): RemoteGroupDataSource {
            val retrofit = RetrofitClient.getClient()
            val groupApi = MockedGroupApi()
            val memberApi = MockedMemberApi()
            val balanceEntryApi = MockedBalanceEntryApi()
            val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
            return RemoteGroupDataSource(groupApi, memberApi, balanceEntryApi, ioDispatcher)
        }
    }
}