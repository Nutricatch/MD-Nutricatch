package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiConfig
import com.nutricatch.dev.data.prefs.Preferences
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.net.UnknownHostException

class RestaurantRepository(private val preferences: Preferences) {
    fun getNearbyRestaurants(lat: Double, lng: Double) = liveData {
        emit(ResultState.Loading)

        try {
            val token = preferences.getToken().first()

            val apiService = ApiConfig.getApiService(token)
            /// TODO ambil dari api jika sudah ada
            val nearbyRestaurants = apiService.getNearbyRestaurants(lat, lng)

            emit(ResultState.Success(nearbyRestaurants))
        } catch (e: HttpException) {
            val errorBody = e.response()?.message()
            when (val code = e.code()) {
                in 300..399 -> {
                    emit(ResultState.Error("Need To Reconfigure. Please Contact Administrator"))
                }

                in 400..499 -> {
                    emit(ResultState.Error("Request Error. code $code $errorBody"))
                }

                in 500..599 -> {
                    emit(ResultState.Error("Server Error"))
                }
            }
        } catch (e: UnknownHostException) {
            emit(ResultState.Error("Error... Please check your connection"))
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }

    }
}