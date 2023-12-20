package com.nutricatch.dev.views.navigation.daily_calories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.DailyIntakeRepository

class DailyCaloriesViewModel(repository: DailyIntakeRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val recommendedRepository = repository.getDailyIntake()
    fun getDailyCalories() {

    }
}