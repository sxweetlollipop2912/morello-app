package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.data_types.groups.Group
import com.example.morello.data_layer.data_sources.data_types.groups.GroupBalance
import com.example.morello.data_layer.data_sources.data_types.groups.GroupDetails
import com.example.morello.data_layer.data_sources.data_types.groups.Leader
import com.example.morello.data_layer.data_sources.data_types.groups.NewGroupRequest
import com.example.morello.data_layer.data_sources.data_types.groups.NewGroupResponse
import com.example.morello.data_layer.data_sources.data_types.groups.UpdateGroupRequest
import com.example.morello.data_layer.data_sources.data_types.groups.UpdateGroupResponse
import okhttp3.ResponseBody
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject

class MockedGroupApi @Inject constructor() : GroupApi {
    private val tempGroups = mutableListOf(
        GroupDetails(
            id = 1,
            name = "group1",
            description = "description1",
            memberCount = 0,
            balance = GroupBalance(0f, 0f),
            recentOpenSessions = emptyList(),
            recentBalanceEntries = emptyList(),
        ),
        GroupDetails(
            id = 2,
            name = "group2",
            description = "description2",
            memberCount = 0,
            balance = GroupBalance(0f, 0f),
            recentOpenSessions = emptyList(),
            recentBalanceEntries = emptyList(),
        ),
        GroupDetails(
            id = 3,
            name = "group3",
            description = "description3",
            memberCount = 0,
            balance = GroupBalance(0f, 0f),
            recentOpenSessions = emptyList(),
            recentBalanceEntries = emptyList(),
        ),
    )

    override suspend fun getManagedGroups(): Response<List<Group>> {
        return Response.success(
            tempGroups.toList().map { group ->
                Group(
                    id = group.id,
                    name = group.name,
                    description = group.description,
                    isLeader = true
                ) }
        )
    }

    override suspend fun getGroupById(id: Int): Response<GroupDetails> {
        val ids = tempGroups.map { it.id }
        if (id !in ids)
            return Response.error(404, ResponseBody.create(null, "error"))
        return Response.success(
            tempGroups.find { it.id == id }!!
        )
    }

    override suspend fun getLeaderByGroupId(id: Int): Response<Leader> {
        return Response.success(Leader(1, "email", "name"))
    }

    override suspend fun updateGroupById(id: Int, group: UpdateGroupRequest): Response<UpdateGroupResponse> {
        if (group.name == "error")
            return Response.error(500, ResponseBody.create(null, "error"))
        return Response.success(
            UpdateGroupResponse(
                id = id,
                name = group.name,
                description = group.description,
                leaderUserId = 0,
                createdAt = LocalDateTime.now(),
            )
        )
    }

    override suspend fun deleteGroupById(id: Int): Response<Group> {
        return Response.success(Group(1, "group", "description", isLeader = false))
    }

    override suspend fun createGroup(group: NewGroupRequest): Response<NewGroupResponse> {
        if (group.name == "error")
            return Response.error(500, ResponseBody.create(null, "error"))
        val newGroup = GroupDetails(
            id = tempGroups.size + 1,
            name = group.name,
            description = group.description,
            memberCount = 0,
            balance = GroupBalance(0f, 0f),
            recentOpenSessions = emptyList(),
            recentBalanceEntries = emptyList(),
        )
        tempGroups.add(newGroup)
        return Response.success(
            NewGroupResponse(
                id = newGroup.id,
                name = newGroup.name,
                description = newGroup.description,
                leaderUserId = 1,
                createdAt = LocalDateTime.now(),
            )
        )
    }
}