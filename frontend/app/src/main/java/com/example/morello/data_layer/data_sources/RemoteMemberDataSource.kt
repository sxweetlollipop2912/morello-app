package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.apis.MemberApi
import com.example.morello.data_layer.data_types.Member
import com.example.morello.data_layer.data_types.MemberCreate
import com.example.morello.data_layer.data_types.MemberDetail
import com.example.morello.data_layer.data_types.MemberUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteMemberDataSource @Inject constructor(
    private val memberApi: MemberApi
) {
    private val dispatcher = Dispatchers.IO

    suspend fun getMembers(groupId: Int): List<Member> {
        return withContext(dispatcher) {
            val res = memberApi.getMembers(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting members")
            }
        }
    }

    suspend fun getMemberDetail(groupId: Int, memberId: Int): MemberDetail {
        return withContext(dispatcher) {
            val res = memberApi.getMemberDetail(groupId, memberId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting member")
            }
        }
    }

    suspend fun createMember(groupId: Int, member: MemberCreate): MemberDetail {
        return withContext(dispatcher) {
            val res = memberApi.createMember(groupId, member)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error adding member")
            }
        }
    }

    suspend fun updateMember(groupId: Int, memberId: Int, member: MemberUpdate): MemberDetail {
        return withContext(dispatcher) {
            val res = memberApi.updateMember(groupId, memberId, member)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error updating member")
            }
        }
    }

    suspend fun deleteMember(groupId: Int, memberId: Int) {
        withContext(dispatcher) {
            val res = memberApi.deleteMember(groupId, memberId)
            if (!res.isSuccessful) {
                throw Exception("Error deleting member")
            }
        }
    }
}