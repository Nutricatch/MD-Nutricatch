package com.nutricatch.dev.views.navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.repository.DailyIntakeRepository
import com.nutricatch.dev.data.repository.UserRepository

class HomeViewModel(private val repository: UserRepository, preferences: Preferences, repository2: DailyIntakeRepository) :
    ViewModel() {

    val token = preferences.getToken().asLiveData()

    val userData = repository.getProfile()

    val dailyIntake = repository2.getDailyIntake()

    val diamonds = repository.getDiamonds()

}