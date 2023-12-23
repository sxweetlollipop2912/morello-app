package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteGroupDataSource
import com.example.morello.data_layer.data_sources.RemoteMemberDataSource
import com.example.morello.data_layer.data_sources.data_types.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.CollectSession
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.Member
import com.example.morello.data_layer.data_sources.data_types.NewGroup
import com.example.morello.data_layer.data_sources.data_types.NewMember
import com.example.morello.data_layer.data_sources.data_types.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val remoteGroupDataSource: RemoteGroupDataSource,
    private val remoteMemberDataSource: RemoteMemberDataSource,
) {
    fun getLeader(groupId: Int): Flow<User> = TODO()
    suspend fun deleteGroup(groupId: Int): Nothing = TODO()
    suspend fun createNewGroup(newGroup: NewGroup, members: List<NewMember>) {
        val groupResponse = remoteGroupDataSource.createNewGroup(newGroup)
        members.forEach {
            remoteMemberDataSource.addMemberToGroup(groupResponse.id, it)
        }
    }

    suspend fun updateGroup(updatedGroup: Group): Nothing = TODO()

    fun getModerators(groupId: Int): Flow<User> = TODO()
    fun getModeratorCorrespondingToMember(groupId: Int, memberId: Int): Flow<User> = TODO()
    suspend fun addModerator(groupId: Int, moderatorId: Int): Nothing = TODO()
    suspend fun removeModerator(groupId: Int, moderatorId: Int): Nothing = TODO()

    fun getCollectSessions(groupId: Int): Flow<List<CollectSession>> = TODO()

    fun getMembers(groupId: Int): Flow<List<Member>> = TODO()
    suspend fun removeMember(groupId: Int, memberId: Int): Nothing = TODO()
    suspend fun addMember(groupId: Int, memberId: Int): Nothing = TODO()

    fun getBalanceEntries(groupId: Int): Flow<List<BalanceEntry>> = TODO()
    suspend fun deleteBalanceEntry(groupId: Int, balanceEntryId: Int): Nothing = TODO()
    suspend fun updateBalanceEntry(groupId: Int, balanceEntry: BalanceEntry): Nothing = TODO()
    suspend fun createBalanceEntry(groupId: Int, balanceEntry: BalanceEntry): Nothing = TODO()
}