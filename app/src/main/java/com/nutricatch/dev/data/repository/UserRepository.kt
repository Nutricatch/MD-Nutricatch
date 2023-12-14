package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.UserModel
import com.nutricatch.dev.data.response.AuthResponse
import retrofit2.HttpException

class UserRepository constructor(val  userPreferences: Preferences, val apiService: ApiService){
    //Auth Method
    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val success = apiService.loginUser(email, password)
            emit(Result.Success(success))
        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AuthResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(Result.Error(errorMessage))
        }
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val success = apiService.registerUser(name, email, password)
            emit(Result.Success(success))
        }
        catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AuthResponse::class.java)
            val errorMessage = errorBody.message.toString()
            emit(Error(errorMessage))
        }
    }
    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

}