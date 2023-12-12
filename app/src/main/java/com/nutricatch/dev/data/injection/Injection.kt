package com.nutricatch.dev.data.injection

import android.content.Context
import com.nutricatch.dev.data.api.ApiConfig
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.data.repository.RecommendationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideRecommendationRepository(context: Context): RecommendationRepository {
        val pref = Preferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token)
        return RecommendationRepository.getInstance(apiService)
    }
}