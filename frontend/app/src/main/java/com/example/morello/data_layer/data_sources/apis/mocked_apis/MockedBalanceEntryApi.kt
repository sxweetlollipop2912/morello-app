package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.data_types.BalanceEntry
import com.example.morello.data_layer.data_sources.data_types.NewBalanceEntry
import com.example.morello.data_layer.data_sources.data_types.UpdatedBalanceEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.time.LocalDate
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
                    expectedAmount = 100,
                    createdAt = LocalDateTime.now(),
                    currentAmount = 0,
                    session = null,
                ),
                BalanceEntry(
                    id = 2,
                    name = "entry2",
                    description = "description2",
                    expectedAmount = 200,
                    createdAt = LocalDateTime.now(),
                    currentAmount = 0,
                    session = null,
                ),
            )
        )
    }

    override suspend fun updateBalanceEntryInGroup(
        id: Int,
        entryId: Int,
        entry: UpdatedBalanceEntry,
    ): Response<BalanceEntry> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (entryId == 0)
            return Response.error(404, ResponseBody.create(null, "error"))
        return Response.success(
            BalanceEntry(
                id = entryId,
                name = entry.name,
                description = entry.description,
                expectedAmount = entry.expectedAmount,
                createdAt = LocalDateTime.now(),
                currentAmount = entry.currentAmount,
                session = null,
            )
        )
    }

    override suspend fun addBalanceEntryToGroup(
        id: Int,
        entry: NewBalanceEntry,
    ): Response<BalanceEntry> {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        if (entry.name == "error")
            return Response.error(500, ResponseBody.create(null, "error"))
        return Response.success(
            BalanceEntry(
                id = 1,
                name = entry.name,
                description = entry.description,
                expectedAmount = entry.expectedAmount,
                createdAt = LocalDateTime.now(),
                currentAmount = 0,
                session = null,
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