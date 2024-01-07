package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.CollectEntryUpdate
import com.example.morello.data_layer.data_types.CollectSession
import com.example.morello.data_layer.data_types.CollectSessionCreate
import com.example.morello.data_layer.data_types.CollectSessionDetail
import com.example.morello.data_layer.data_types.CollectSessionUpdate
import com.example.morello.data_layer.data_types.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CollectSessionApi {
    @GET("groups/{groupId}/sessions/")
    suspend fun getCollectSessions(@Path("groupId") groupId: Int): Response<List<CollectSession>>

    @GET("groups/{groupId}/sessions/{sessionId}/")
    suspend fun getCollectSessionDetail(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
    ): Response<CollectSessionDetail>

    @POST("groups/{groupId}/sessions/")
    suspend fun createCollectSession(
        @Path("groupId") groupId: Int,
        @Body collectSession: CollectSessionCreate
    ): Response<CollectSessionDetail>

    @PUT("groups/{groupId}/sessions/{sessionId}/")
    suspend fun updateCollectSession(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
        @Body collectSession: CollectSessionUpdate,
    ): Response<CollectSessionDetail>

    @DELETE("groups/{groupId}/sessions/{sessionId}/")
    suspend fun deleteCollectSession(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int
    ): Response<Void>

    // ------------------------------------------------------------
    @POST("groups/{groupId}/sessions/{sessionId}/close/")
    suspend fun closeCollectSession(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int
    ): Response<Void>

    @POST("groups/{groupId}/sessions/{sessionId}/members/{memberId}/status/")
    suspend fun updateCollectEntry(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
        @Path("memberId") memberId: Int,
        @Body collectSessionEntry: CollectEntryUpdate,
    ): Response<MessageResponse>

}