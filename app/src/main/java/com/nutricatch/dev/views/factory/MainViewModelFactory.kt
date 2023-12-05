package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.PostRepository
import com.nutricatch.dev.views.navigation.home.HomeViewModel

class MainViewModelFactory(private val repository: PostRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        fun getInstance(context: Context): MainViewModelFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MainViewModelFactory(Injection.providePostRepository(context))
        }.also { INSTANCE= it }
    }
}