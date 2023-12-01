package com.nutricatch.dev.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.views.loading.LoadingViewModel

class ViewModelFactory(private val preferences: Preferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadingViewModel::class.java)) {
            return LoadingViewModel(preferences) as T
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }
}