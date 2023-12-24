package com.example.morello.data_layer.data_sources.apis.mocked_apis

import android.util.Log
import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.NewGroup
import com.example.morello.data_layer.data_sources.data_types.User
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class MockedGroupApi @Inject constructor() : GroupApi {
    private val tempGroups = mutableListOf<Group>(
        Group(1, "group1", "description1", isLeader = true),
    )

    override suspend fun getManagedGroups(): Response<List<Group>> {
        return Response.success(
            tempGroups.toList()
        )
    }

    override suspend fun getGroupById(id: Int): Response<Group> {
        val ids = tempGroups.map { it.id }
        if (id !in ids)
            return Response.error(404, ResponseBody.create(null, "error"))
        return Response.success(
            tempGroups.find { it.id == id }!!
        )
    }

    override suspend fun getLeaderByGroupId(id: Int): Response<User> {
        return Response.success(User(1, "user", "email"))
    }

    override suspend fun updateGroupById(id: Int, group: Group): Response<Group> {
        if (group.name == "error")
            return Response.error(500, ResponseBody.create(null, "error"))
        return Response.success(
            Group(
                id = id,
                name = group.name,
                description = group.description,
                isLeader = false
            )
        )
    }

    override suspend fun deleteGroupById(id: Int): Response<Group> {
        return Response.success(Group(1, "group", "description", isLeader = false))
    }

    override suspend fun createGroup(group: NewGroup): Response<Group> {
        if (group.name == "error")
            return Response.error(500, ResponseBody.create(null, "error"))
        val newGroup = Group(
            id = tempGroups.size + 1,
            name = group.name,
            description = group.description,
            isLeader = true
        )
        tempGroups.add(newGroup)
        Log.d("MockedGroupApi", tempGroups.toString())
        return Response.success(newGroup)
    }
}