package com.example.morello.data_layer.data_sources

import com.example.morello.data_layer.apis.BalanceApi
import com.example.morello.data_layer.data_types.Balance
import com.example.morello.data_layer.data_types.BalanceEntry
import com.example.morello.data_layer.data_types.BalanceEntryCreate
import com.example.morello.data_layer.data_types.BalanceEntryDetail
import com.example.morello.data_layer.data_types.BalanceEntryUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteBalanceDataSource @Inject constructor(
    private val balanceApi: BalanceApi,
) {
    private val dispatcher = Dispatchers.IO

    suspend fun getBalance(groupId: Int): Balance {
        return withContext(dispatcher) {
            val res = balanceApi.getBalance(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting balance")
            }
        }
    }

    suspend fun getBalanceEntries(groupId: Int): List<BalanceEntry> {
        return withContext(dispatcher) {
            val res = balanceApi.getBalanceEntries(groupId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting balance entries")
            }
        }
    }

    suspend fun getBalanceEntryDetail(groupId: Int, entryId: Int): BalanceEntryDetail {
        return withContext(dispatcher) {
            val res = balanceApi.getBalanceEntryDetail(groupId, entryId)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error getting balance entry")
            }
        }
    }

    suspend fun createBalanceEntry(groupId: Int, entry: BalanceEntryCreate): BalanceEntryDetail {
        return withContext(dispatcher) {
            val res = balanceApi.createBalanceEntry(groupId, entry)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error creating balance entry")
            }
        }
    }

    suspend fun updateBalanceEntry(
        groupId: Int,
        entryId: Int,
        entry: BalanceEntryUpdate
    ): BalanceEntryDetail {
        return withContext(dispatcher) {
            val res = balanceApi.updateBalanceEntry(groupId, entryId, entry)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                throw Exception("Error updating balance entry")
            }
        }
    }

    suspend fun deleteBalanceEntry(groupId: Int, entryId: Int) {
        withContext(dispatcher) {
            val res = balanceApi.deleteBalanceEntry(groupId, entryId)
            if (!res.isSuccessful) {
                throw Exception("Error deleting balance entry")
            }
        }
    }
}