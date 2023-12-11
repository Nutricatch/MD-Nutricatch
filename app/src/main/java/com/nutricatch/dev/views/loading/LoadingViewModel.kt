package com.nutricatch.dev.views.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nutricatch.dev.data.prefs.Preferences
import kotlinx.coroutines.flow.Flow

class LoadingViewModel(private val preferences: Preferences) : ViewModel() {
    fun isOnBoard(): Flow<Boolean> {
        return preferences.isOnBoard()
    }

    fun getToken(): Flow<String?> {
        return preferences.getToken()
    }

    val theme = preferences.themeMode.asLiveData()
}