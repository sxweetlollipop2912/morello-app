package com.example.morello.data_layer.repositories

import com.example.morello.data_layer.data_sources.RemoteBalanceDataSource
import javax.inject.Inject

class BalanceEntryRepository @Inject constructor(
    private val remoteBalanceDataSource: RemoteBalanceDataSource,
) {
    suspend fun getBalanceEntries(groupId: Int) =
        remoteBalanceDataSource.getBalanceEntries(groupId)
}