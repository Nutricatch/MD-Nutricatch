package com.nutricatch.dev.views.navigation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nutricatch.dev.data.api.response.ConsumeResponse
import com.nutricatch.dev.data.api.response.RecommendedNutritionResponse
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.repository.DailyIntakeRepository
import com.nutricatch.dev.data.repository.UserRepository
import com.nutricatch.dev.utils.todayDate

class HomeViewModel(
    private val repository: UserRepository,
    preferences: Preferences,
    private val repository2: DailyIntakeRepository
) :
    ViewModel() {
    private val _recommendedNutrition = MutableLiveData<RecommendedNutritionResponse>()
    private val _todayResult = MutableLiveData<ConsumeResponse>()

    val todayResult: LiveData<ConsumeResponse> = _todayResult

    private var _recommendedValue = RecommendedNutritionResponse()
    private var _todayConsumeValue = ConsumeResponse()

    val token = preferences.getToken().asLiveData()

    val userData = repository.getProfile()

    val dailyIntake = repository2.getDailyIntake(todayDate)

    val todayConsumes = repository2.getTodayConsumption()

    fun getRecommendedNutrition() = repository.getRecommendedNutrition()

    fun setRecommendedNutrition(recommendedNutritionResponse: RecommendedNutritionResponse) {
        _recommendedNutrition.value = recommendedNutritionResponse
        _recommendedValue = recommendedNutritionResponse
    }

    fun setTodayConsume(consumeResponses: List<ConsumeResponse>) {
        var fats = 0.0
        var carbs = 0.0
        var proteins = 0.0

        val recFats = _recommendedValue.fats?.toDoubleOrNull() ?: 0.0
        val recCarbs = _recommendedValue.carbohydrates?.toDoubleOrNull() ?: 0.0
        val recProteins = _recommendedValue.protein?.toDoubleOrNull() ?: 0.0

        consumeResponses.forEach {
            fats += it.fat ?: 0.0
            carbs += it.carbohydrates ?: 0.0
            proteins += it.protein ?: 0.0
        }

        _todayResult.value =
            ConsumeResponse(
                fat = fats / recFats,
                carbohydrates = carbs / recCarbs,
                protein = proteins / recProteins
            )
        Log.d("TAG", "onViewCreated: $fats $recFats $carbs $recCarbs $proteins $recProteins")
    }

    val diamonds = repository.getDiamonds()

}