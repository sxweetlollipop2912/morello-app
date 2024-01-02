package com.example.morello.data_layer.data_sources

import android.util.Log
import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.apis.client.ErrorResponse
import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryRequest
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryResponse
import com.example.morello.data_layer.data_sources.data_types.balance.UpdateBalanceEntryRequest
import com.example.morello.data_layer.data_sources.data_types.balance.UpdateBalanceEntryResponse
import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSession
import com.example.morello.data_layer.data_sources.data_types.groups.Group
import com.example.morello.data_layer.data_sources.data_types.groups.GroupDetails
import com.example.morello.data_layer.data_sources.data_types.groups.Leader
import com.example.morello.data_layer.data_sources.data_types.groups.NewGroupRequest
import com.example.morello.data_layer.data_sources.data_types.groups.NewGroupResponse
import com.example.morello.data_layer.data_sources.data_types.groups.UpdateGroupRequest
import com.example.morello.data_layer.data_sources.data_types.groups.UpdateGroupResponse
import com.example.morello.data_layer.data_sources.data_types.members.Member
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberRequest
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberResponse
import com.example.morello.data_layer.data_sources.data_types.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateGroupException : Exception()
class UpdateGroupException : Exception()
class DeleteGroupException : Exception()
class GetGroupException : Exception()
data class CreateBalanceEntryException(private val msg: String) : Exception() {
    override val message: String
        get() = msg
}

data class UpdateBalanceEntryException(private val msg: String) : Exception() {
    override val message: String
        get() = msg
}

data class DeleteBalanceEntryException(private val msg: String) : Exception() {
    override val message: String
        get() = msg

}

data class GetBalanceEntryException(private val msg: String) : Exception() {
    override val message: String
        get() = msg
}


class RemoteGroupDataSource @Inject constructor(
    private val groupApi: GroupApi,
    private val memberApi: MemberApi,
    private val balanceEntryApi: BalanceEntryApi,
) {
    private val dispatcher = Dispatchers.IO

    suspend fun getGroupById(id: Int): GroupDetails {
        return withContext(dispatcher) {
            val res = groupApi.getGroupById(id)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting group")
            }
        }
    }

    suspend fun getLeader(groupId: Int): Leader {
        return withContext(dispatcher) {
            val res = groupApi.getLeaderByGroupId(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting leader")
            }
        }
    }

    suspend fun deleteGroup(groupId: Int): Group {
        return withContext(dispatcher) {
            val res = groupApi.deleteGroupById(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error deleting group")
            }
        }
    }

    suspend fun createNewGroup(newGroupRequest: NewGroupRequest): NewGroupResponse {
        return withContext(dispatcher) {
            val res = groupApi.createGroup(newGroupRequest)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error creating group")
            }
        }
    }

    suspend fun updateGroup(id: Int, updateGroupRequest: UpdateGroupRequest): UpdateGroupResponse {
        return withContext(dispatcher) {
            val res = groupApi.updateGroupById(id, updateGroupRequest)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error updating group")
            }
        }
    }

    suspend fun getManagedGroups(): List<Group> {
        return withContext(dispatcher) {
            val res = groupApi.getManagedGroups()
            Log.d("RemoteGroupDataSource", res.toString())
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting managed groups")
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
                throw Exception("Error getting members: $err")
            }
        }
    }

    suspend fun removeMember(groupId: Int, memberId: Int): Member {
        return withContext(dispatcher) {
            val res = memberApi.deleteMemberFromGroup(groupId, memberId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error removing member: $err")
            }
        }
    }

    suspend fun addMember(groupId: Int, member: NewMemberRequest): NewMemberResponse {
        return withContext(dispatcher) {
            val res = memberApi.addMemberToGroup(groupId, member)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error adding member: $err")
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
                throw GetBalanceEntryException("Error getting balance entries: $err")
            }
        }
    }

    suspend fun deleteBalanceEntry(groupId: Int, balanceEntryId: Int): BalanceEntry {
        return withContext(dispatcher) {
            val res = balanceEntryApi.deleteBalanceEntryFromGroup(groupId, balanceEntryId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw DeleteBalanceEntryException("Error deleting balance entry: $err")
            }
        }
    }

    suspend fun updateBalanceEntry(
        groupId: Int,
        balanceEntryId: Int,
        balanceEntry: UpdateBalanceEntryRequest
    ): UpdateBalanceEntryResponse {
        return withContext(dispatcher) {
            val res =
                balanceEntryApi.updateBalanceEntryInGroup(groupId, balanceEntryId, balanceEntry)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw UpdateBalanceEntryException("Error updating balance entry: $err")
            }
        }
    }

    suspend fun createBalanceEntry(
        groupId: Int,
        balanceEntry: NewBalanceEntryRequest
    ): NewBalanceEntryResponse {
        return withContext(dispatcher) {
            val res = balanceEntryApi.addBalanceEntryToGroup(groupId, balanceEntry)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw CreateBalanceEntryException("Error creating balance entry: $err")
            }
        }
    }
}