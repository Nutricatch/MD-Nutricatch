package com.nutricatch.dev.views.navigation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.utils.Theme
import kotlinx.coroutines.launch

class ProfileViewModel(private val preferences: Preferences) : ViewModel() {

    val token: LiveData<String?> = preferences.getToken().asLiveData()

    private val _locale = MutableLiveData<String>().apply {
        viewModelScope.launch {
            preferences.appLocale.collect {
                value = it
            }
        }
    }

    var locale = _locale

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