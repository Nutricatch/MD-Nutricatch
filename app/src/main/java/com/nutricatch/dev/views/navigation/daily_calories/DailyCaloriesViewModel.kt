package com.nutricatch.dev.views.navigation.daily_calories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.api.response.ConsumeResponse
import com.nutricatch.dev.data.api.response.RecommendedNutritionResponse
import com.nutricatch.dev.data.repository.DailyIntakeRepository
import com.nutricatch.dev.utils.todayDate

class DailyCaloriesViewModel(private val repository: DailyIntakeRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _dailyData = MutableLiveData<List<ConsumeResponse>>()
    val dailyData: LiveData<List<ConsumeResponse>> = _dailyData

    fun getDailyData(date: String? = todayDate) = repository.getDailyIntake(date!!)

    fun setDailyData(dailies: List<ConsumeResponse>) {
        _dailyData.value = dailies
    }

    fun getRecommendedNutrients() = repository.getRecommendedNutrients()


}