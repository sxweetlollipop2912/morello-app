package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.GroupApi
import com.example.morello.data_layer.data_sources.data_types.Group
import com.example.morello.data_layer.data_sources.data_types.User
import retrofit2.Response

class MockedGroupApi : GroupApi {
    override suspend fun getGroupById(id: Int): Response<Group> {
        return Response.success(Group(1, "group", "description"))
    }

    override suspend fun getLeaderByGroupId(id: Int): Response<User> {
        return Response.success(User(1, "username", "email"))
    }

    override suspend fun updateGroupById(id: Int, group: Group): Response<Group> {
        return Response.success(Group(1, "group", "description"))
    }

    override suspend fun deleteGroupById(id: Int): Response<Group> {
        return Response.success(Group(1, "group", "description"))
    }

    override suspend fun createGroup(group: Group): Response<Group> {
        return Response.success(Group(1, "group", "description"))
    }
}