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
import com.example.morello.data_layer.data_types.GroupCreate
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

    suspend fun createGroup(groupCreate: GroupCreate) =
        remoteGroupDataSource.createGroup(groupCreate)
}