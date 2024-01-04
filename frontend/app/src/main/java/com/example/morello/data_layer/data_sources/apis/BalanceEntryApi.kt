package com.example.morello.data_layer.data_sources.apis

import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntryCreate
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryResponse
import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntryUpdate
import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntryDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BalanceEntryApi {
    @GET("groups/{id}/entries/")
    suspend fun getBalanceEntriesByGroupId(@Path("id") id: Int): Response<List<BalanceEntry>>

    @PUT("groups/{id}/entries/{entryId}/")
    suspend fun updateBalanceEntryInGroup(
        @Path("id") id: Int,
        @Path("entryId") entryId: Int,
        @Body entry: BalanceEntryUpdate,
    ): Response<BalanceEntryDetail>

    @POST("groups/{id}/entries/")
    suspend fun addBalanceEntryToGroup(
        @Path("id") id: Int,
        @Body entry: BalanceEntryCreate,
    ): Response<NewBalanceEntryResponse>

    @DELETE("groups/{id}/entries/{entryId}/")
    suspend fun deleteBalanceEntryFromGroup(
        @Path("id") id: Int,
        @Path("entryId") entryId: Int
    ): Response<BalanceEntry>
}