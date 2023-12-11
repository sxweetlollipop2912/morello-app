package com.example.morello.data_layer.data_sources

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingDataSource(private val context: Context) {
    private val tokenKey = stringPreferencesKey("token")
    fun getToken(): Flow<String> {
        return context.dataStore.data.map {
            it[tokenKey] ?: ""
        }
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit {
            it[tokenKey] = token
        }
    }
}