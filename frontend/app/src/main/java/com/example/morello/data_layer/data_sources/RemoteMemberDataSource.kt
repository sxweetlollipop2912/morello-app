package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.data_sources.apis.MemberApi
import com.example.morello.data_layer.data_sources.apis.client.ErrorResponse
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberRequest
import com.example.morello.data_layer.data_sources.data_types.members.NewMemberResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteMemberDataSource @Inject constructor(
    private val memberApi: MemberApi
) {
    private val dispatcher = Dispatchers.IO

    suspend fun addMemberToGroup(groupId: Int, member: NewMemberRequest): NewMemberResponse {
        return withContext(dispatcher) {
            val res = memberApi.addMemberToGroup(groupId, member)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                val err = ErrorResponse.fromResponseBody(res.errorBody())
                throw Exception("Error adding member to group: $err")
            }
        }
    }
}