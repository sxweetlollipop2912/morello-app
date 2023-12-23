package com.example.morello.data_layer.data_sources.apis.mocked_apis

import com.example.morello.data_layer.data_sources.apis.BalanceEntryApi
import com.example.morello.data_layer.data_sources.data_types.BalanceEntry
import retrofit2.Response
import javax.inject.Inject

class MockedBalanceEntryApi @Inject constructor() : BalanceEntryApi {
    override suspend fun getBalanceEntriesByGroupId(id: Int): Response<List<BalanceEntry>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBalanceEntryInGroup(
        id: Int,
        entryId: Int,
        entry: BalanceEntry
    ): Response<BalanceEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun addBalanceEntryToGroup(
        id: Int,
        entry: BalanceEntry
    ): Response<BalanceEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBalanceEntryFromGroup(
        id: Int,
        entryId: Int
    ): Response<BalanceEntry> {
        TODO("Not yet implemented")
    }
}