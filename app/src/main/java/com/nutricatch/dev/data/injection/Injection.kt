package com.nutricatch.dev.data.injection

import android.content.Context
import com.nutricatch.dev.data.api.ApiConfig
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.data.repository.PostRepository
import com.nutricatch.dev.data.repository.RecipesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun providePostRepository(context: Context): PostRepository {
        val pref = Preferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token)
        return PostRepository.getInstance(apiService)
    }

    fun provideRecipeRepository(context: Context): RecipesRepository {
        val pref = Preferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token)
        return RecipesRepository.getInstance(apiService)
    }
}