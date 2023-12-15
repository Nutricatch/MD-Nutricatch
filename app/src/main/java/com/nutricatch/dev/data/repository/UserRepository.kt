package com.nutricatch.dev.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.response.AuthResponse
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreferences: Preferences,
    private val apiService: ApiService
) {
    //Auth Method
    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val success = apiService.loginUser(email, password)
            emit(ResultState.Success(success))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AuthResponse::class.java)
            val errorMessage = errorBody.message.toString()
            val responseCode = e.code()
            if (responseCode == 401) {
                emit(ResultState.Error("Wrong combination of email and password"))
            } else {
                emit(ResultState.Error(errorMessage))
            }
        } catch (e: Exception) {
            Log.d("TAG AUTH", "login: $e")
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val success = apiService.registerUser(name, email, password)
            emit(ResultState.Success(success))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AuthResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(ResultState.Error(errorMessage))
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    suspend fun saveSession(token: String) {
        userPreferences.saveSession(token)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(preferences: Preferences, apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(preferences, apiService)
            }.also { INSTANCE = it }
    }
}