package com.example.budgetlyapp.common.dataStore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.budgetlyapp.common.utils.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

interface DataStoreTask {
    suspend fun setString(key: String, value: String)
    suspend fun setDouble(key: String, value: Double)
    suspend fun setBoolean(key: String, value: Boolean)
    suspend fun getString(key: String): String
    suspend fun getDouble(key: String): Double
    suspend fun getBoolean(key: String): Boolean
}

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) :
    DataStoreTask {
    override suspend fun setString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun setDouble(key: String, value: Double) {
        val preferencesKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String {
        val preferenceKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()

        return preferences[preferenceKey] ?: ""
    }

    override suspend fun getDouble(key: String): Double {
        val preferenceKey = doublePreferencesKey(key)
        val preferences = context.dataStore.data.first()

        return preferences[preferenceKey] ?: 0.0
    }

    override suspend fun getBoolean(key: String): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        val preferences = context.dataStore.data.first()

        return preferences[preferencesKey] ?: true
    }
}