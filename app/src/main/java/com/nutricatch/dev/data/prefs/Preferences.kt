package com.nutricatch.dev.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nutricatch.dev.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
* File ini digunakan untuk menyimpan token dan sesi login
* */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

class Preferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val tokenKey = stringPreferencesKey(Const.TOKEN_NAME)
    private val onBoard = booleanPreferencesKey(Const.ON_BOARD)

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[tokenKey] = token
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { it[tokenKey] }
    }

    suspend fun deleteToken(): Boolean {
        return try {
            dataStore.edit {
                it.remove(tokenKey)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isOnBoard(): Flow<Boolean> {
        return dataStore.data.map { it[onBoard] ?: true }
    }

    suspend fun boarding() {
        dataStore.edit { it[onBoard] = true }
    }

    companion object {
        @Volatile
        private var INSTANCE: com.nutricatch.dev.data.prefs.Preferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): com.nutricatch.dev.data.prefs.Preferences {
            return INSTANCE ?: synchronized(this) {
                val instance = Preferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}