package com.example.morello.data_layer.apis

import com.example.morello.data_layer.data_types.Balance
import com.example.morello.data_layer.data_types.BalanceEntry
import com.example.morello.data_layer.data_types.BalanceEntryCreate
import com.example.morello.data_layer.data_types.BalanceEntryUpdate
import com.example.morello.data_layer.data_types.BalanceEntryDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BalanceApi {
    @GET("groups/{groupId}/balance/")
    suspend fun getBalance(@Path("groupId") groupId: Int): Response<Balance>

    @GET("groups/{groupId}/balance/entries/")
    suspend fun getBalanceEntries(@Path("groupId") groupId: Int): Response<List<BalanceEntry>>

    @GET("groups/{groupId}/balance/entries/{entryId}/")
    suspend fun getBalanceEntryDetail(
        @Path("groupId") groupId: Int,
        @Path("entryId") entryId: Int,
    ): Response<BalanceEntryDetail>

    @POST("groups/{groupId}/balance/entries/")
    suspend fun createBalanceEntry(
        @Path("groupId") groupId: Int,
        @Body entry: BalanceEntryCreate,
    ): Response<BalanceEntryDetail>

    @PUT("groups/{groupId}/balance/entries/{entryId}/")
    suspend fun updateBalanceEntry(
        @Path("groupId") groupId: Int,
        @Path("entryId") entryId: Int,
        @Body entry: BalanceEntryUpdate,
    ): Response<BalanceEntryDetail>

    @DELETE("groups/{groupId}/entries/{entryId}/")
    suspend fun deleteBalanceEntry(
        @Path("groupId") groupId: Int,
        @Path("entryId") entryId: Int
    ): Response<Void>
}