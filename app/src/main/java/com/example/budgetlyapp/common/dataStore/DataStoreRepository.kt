package com.example.budgetlyapp.common.dataStore

import android.content.Context
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
    suspend fun putUserName(key: String, value: String)
    suspend fun putIncomeValue(key: String, value: Double)
    suspend fun getUserName(key: String): String
    suspend fun getIncomeValue(key: String): Double
}

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) :
    DataStoreTask {
    override suspend fun putUserName(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putIncomeValue(key: String, value: Double) {
        val preferencesKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getUserName(key: String): String {
        val preferenceKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()

        return preferences[preferenceKey] ?: ""
    }

    override suspend fun getIncomeValue(key: String): Double {
        val preferenceKey = doublePreferencesKey(key)
        val preferences = context.dataStore.data.first()

        return preferences[preferenceKey] ?: 0.0
    }
}