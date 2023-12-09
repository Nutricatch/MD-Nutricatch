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

    val theme: LiveData<Theme> = preferences.themeMode.asLiveData()

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            preferences.changeTheme(theme)
        }
    }
}