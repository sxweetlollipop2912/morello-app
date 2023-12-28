package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.data_types.collect_sessions.CollectSessionEntry
import com.example.morello.data_layer.data_sources.data_types.members.Member
import kotlinx.coroutines.flow.Flow

class MemberRepository {
    fun getParticipatedGroups(userId: Int): Flow<List<Int>> = TODO()
    fun getMemberById(id: Int): Flow<Member> = TODO()
    fun getCollectSessionEntries(groupId: Int, memberId: Int): Flow<List<CollectSessionEntry>> =
        TODO()
}