package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.CollectSession
import com.example.morello.data_layer.data_sources.data_types.CollectSessionEntry
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CollectSessionApi {
    @GET("groups/{id}/sessions")
    suspend fun getCollectSessionsByGroupId(@Path("id") id: Int): Call<List<CollectSession>>

    @POST("groups/{id}/sessions")
    suspend fun addCollectSessionToGroup(
        @Path("id") id: Int,
        @Body collectSession: CollectSession
    ): Call<CollectSession>

    @GET("groups/{groupId}/sessions/{sessionId}/entries")
    suspend fun getCollectSessionEntriesBySessionId(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
    ): Call<List<CollectSessionEntry>>

    @POST("groups/{groupId}/sessions/{sessionId}/entries")
    suspend fun addCollectSessionEntry(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
        @Body collectSessionEntry: CollectSessionEntry,
    ): Call<CollectSessionEntry>

    @PUT("groups/{groupId}/sessions/{sessionId}/entries/{entryId}")
    suspend fun updateCollectSessionEntry(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
        @Path("entryId") entryId: Int,
        @Body collectSessionEntry: CollectSessionEntry,
    ): Call<CollectSession>

    @PUT("groups/{groupId}/sessions/{sessionId}")
    suspend fun updateCollectSession(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int,
        @Body collectSession: CollectSession,
    ): Call<CollectSession>

    @DELETE("groups/{groupId}/sessions/{sessionId}")
    suspend fun deleteCollectSession(
        @Path("groupId") groupId: Int,
        @Path("sessionId") sessionId: Int
    ): Call<CollectSession>
}