package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.data_types.balance.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryRequest
import com.example.morello.data_layer.data_sources.data_types.balance.NewBalanceEntryResponse
import com.example.morello.data_layer.data_sources.data_types.balance.UpdateBalanceEntryRequest
import com.example.morello.data_layer.data_sources.data_types.balance.UpdateBalanceEntryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.time.LocalDateTime
import javax.inject.Inject

class MockedBalanceEntryApi @Inject constructor() : BalanceEntryApi {
    override suspend fun getBalanceEntriesByGroupId(id: Int): Response<List<BalanceEntry>> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (id == 0)
            return Response.error(404, ResponseBody.create(null, "error"))
        return Response.success(
            listOf(
                BalanceEntry(
                    id = 1,
                    name = "entry",
                    description = "description",
                    amount = 100000f,
                    createdAt = LocalDateTime.now(),
                    session = null,
                ),
                BalanceEntry(
                    id = 2,
                    name = "entry2",
                    description = "description2",
                    amount = 200000f,
                    createdAt = LocalDateTime.now(),
                    session = null,
                ),
            )
        )
    }

    override suspend fun updateBalanceEntryInGroup(
        id: Int,
        entryId: Int,
        entry: UpdateBalanceEntryRequest,
    ): Response<UpdateBalanceEntryResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (entryId == 0)
            return Response.error(404, ResponseBody.create(null, "error"))
        return Response.success(
            UpdateBalanceEntryResponse(
                id = entryId,
                name = entry.name,
                description = entry.description,
                amount = entry.amount,
                createdAt = LocalDateTime.now()
            )
        )
    }

    override suspend fun addBalanceEntryToGroup(
        id: Int,
        entry: NewBalanceEntryRequest,
    ): Response<NewBalanceEntryResponse> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (entry.name == "error")
            return Response.error(500, ResponseBody.create(null, "error"))
        return Response.success(
            NewBalanceEntryResponse(
                id = 1,
                name = entry.name,
                description = entry.description,
                amount = entry.amount,
                createdAt = LocalDateTime.now(),
            )
        )
    }

    override suspend fun deleteBalanceEntryFromGroup(
        id: Int,
        entryId: Int
    ): Response<BalanceEntry> {
        TODO("Not yet implemented")
    }
}