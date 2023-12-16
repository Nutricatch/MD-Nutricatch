package com.nutricatch.dev.views.navigation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nutricatch.dev.data.api.response.ActivityLevel
import com.nutricatch.dev.data.api.response.FitnessGoal
import com.nutricatch.dev.data.api.response.Gender
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.repository.UserRepository
import com.nutricatch.dev.utils.Theme
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val preferences: Preferences,
    private val userRepository: UserRepository
) : ViewModel() {

    val token: LiveData<String?> = preferences.getToken().asLiveData()

    private val _locale = MutableLiveData<String>().apply {
        viewModelScope.launch {
            preferences.appLocale.collect {
                value = it
            }
        }
    }

    val locale: LiveData<String> = _locale

    val theme = preferences.themeMode

    val userProfile = userRepository.getProfile()

    val userHealthData = userRepository.getUserHealth()

    fun updateHealthData(
        weight: Double,
        height: Double,
        age: Double,
        gender: Gender,
        fitnessGoal: FitnessGoal,
        activityLevel: ActivityLevel
    ) = userRepository.updateHealthProfile(weight, height, age, gender, fitnessGoal, activityLevel)

    fun logout() {
        viewModelScope.launch {
            preferences.deleteToken()
        }
    }

    fun setLocale(locale: String) {
        viewModelScope.launch {
            preferences.setLocale(locale)
        }
    }

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            preferences.changeTheme(theme)
        }
    }
}