package com.example.morello.data_layer.repositories

import android.util.Log
import com.example.morello.data_layer.data_sources.RemoteBalanceDataSource
import com.example.morello.data_layer.data_sources.RemoteGroupDataSource
import com.example.morello.data_layer.data_sources.RemoteMemberDataSource
import com.example.morello.data_layer.data_types.Balance
import com.example.morello.data_layer.data_types.BalanceEntry
import com.example.morello.data_layer.data_types.BalanceEntryCreate
import com.example.morello.data_layer.data_types.BalanceEntryDetail
import com.example.morello.data_layer.data_types.Group
import com.example.morello.data_layer.data_types.GroupDetail
import com.example.morello.data_layer.data_types.GroupUpdate
import com.example.morello.data_layer.data_types.Member
import com.example.morello.data_layer.data_types.MemberCreate
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val remoteGroupDataSource: RemoteGroupDataSource,
    private val remoteBalanceDataSource: RemoteBalanceDataSource,
    private val remoteMemberDataSource: RemoteMemberDataSource,
) {
    suspend fun getGroups(): List<Group> {
        return remoteGroupDataSource.getGroups()
    }

    suspend fun createBalanceEntry(groupId: Int, balanceEntry: BalanceEntryCreate) {
        remoteBalanceDataSource.createBalanceEntry(groupId, balanceEntry)
    }

    suspend fun getBalanceEntry(groupId: Int, entryId: Int) : BalanceEntryDetail {
        return remoteBalanceDataSource.getBalanceEntryDetail(groupId, entryId)
    }

    suspend fun getGroupDetail(groupId: Int): GroupDetail {
        return remoteGroupDataSource.getGroup(groupId)
    }

    suspend fun updateGroup(groupId: Int, group: GroupUpdate) {
        remoteGroupDataSource.updateGroup(groupId, group)
    }

    suspend fun getGroupBalance(groupId: Int): Balance {
        return remoteBalanceDataSource.getBalance(groupId)
    }

    suspend fun getMembers(groupId: Int): List<Member> {
        return remoteMemberDataSource.getMembers(groupId)
    }

    suspend fun createMember(groupId: Int, member: MemberCreate) {
        remoteMemberDataSource.createMember(groupId, member)
    }

    suspend fun deleteMember(groupId: Int, memberId: Int) {
        remoteMemberDataSource.deleteMember(groupId, memberId)
    }
//    fun getLeader(groupId: Int): Flow<User> = TODO()
//    suspend fun deleteGroup(groupId: Int): Nothing = TODO()
//    suspend fun createNewGroup(newGroupRequest: NewGroupRequest, members: List<NewMemberRequest>) {
//        val groupResponse = remoteGroupDataSource.createGroup(newGroupRequest)
//        members.forEach {
//            remoteMemberDataSource.createMember(groupResponse.id, it)
//        }
//    }
//
//    suspend fun getManagedGroups(): List<Group> {
////        userRepository.isLoggedIn.collectLatest { loggedIn ->
////            if (!loggedIn) {
////                throw Exception("User not logged in")
////            }
////        }
//        return remoteGroupDataSource.getManagedGroups()
//    }
//
//    suspend fun updateGroup(updatedGroup: Group): Nothing = TODO()
//
//    fun getModerators(groupId: Int): Flow<User> = TODO()
//    fun getModeratorCorrespondingToMember(groupId: Int, memberId: Int): Flow<User> = TODO()
//    suspend fun addModerator(groupId: Int, moderatorId: Int): Nothing = TODO()
//    suspend fun removeModerator(groupId: Int, moderatorId: Int): Nothing = TODO()
//
//    fun getCollectSessions(groupId: Int): Flow<List<CollectSession>> = TODO()
//
//    fun getMembers(groupId: Int): Flow<List<Member>> = TODO()
//    suspend fun removeMember(groupId: Int, memberId: Int): Nothing = TODO()
//    suspend fun addMember(groupId: Int, memberId: Int): Nothing = TODO()
//
//    suspend fun getBalanceEntries(groupId: Int): List<BalanceEntry> {
//        return remoteGroupDataSource.getBalanceEntries(groupId)
//    }
//
//    suspend fun deleteBalanceEntry(groupId: Int, balanceEntryId: Int): Nothing = TODO()
//    suspend fun updateBalanceEntry(
//        groupId: Int,
//        balanceEntryId: Int,
//        balanceEntry: BalanceEntryUpdate
//    ) {
//        remoteGroupDataSource.updateBalanceEntry(groupId, balanceEntryId, balanceEntry)
//    }

//    suspend fun createBalanceEntry(groupId: Int, balanceEntry: BalanceEntryCreate) {
//        remoteGroupDataSource.createBalanceEntry(groupId, balanceEntry)
//    }
}