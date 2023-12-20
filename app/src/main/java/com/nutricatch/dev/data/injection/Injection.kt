package com.nutricatch.dev.data.injection

import android.content.Context
import com.nutricatch.dev.data.api.ApiConfig
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.data.repository.AuthRepository
import com.nutricatch.dev.data.repository.FoodsRepository
import com.nutricatch.dev.data.repository.RecipesRepository
import com.nutricatch.dev.data.repository.RestaurantRepository
import com.nutricatch.dev.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun updateToken(token: String) {

    }

    fun provideRecipeRepository(context: Context): RecipesRepository {
        val pref = Preferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token)
        return RecipesRepository.getInstance(apiService)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = Preferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token)
        return AuthRepository.getInstance(pref, apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = Preferences.getInstance(context.dataStore)
        val token = runBlocking { pref.getToken().first() }
        val apiService = ApiConfig.getApiService(token)
        return UserRepository(apiService)
    }

    fun provideRestaurantRepository(context: Context): RestaurantRepository {
        val preferences = Preferences.getInstance(context.dataStore)
        return RestaurantRepository(preferences)
    }

    fun provideFoodsRepository(context: Context): FoodsRepository {
        val preferences = Preferences.getInstance(context.dataStore)
        return FoodsRepository(preferences)
    }
}