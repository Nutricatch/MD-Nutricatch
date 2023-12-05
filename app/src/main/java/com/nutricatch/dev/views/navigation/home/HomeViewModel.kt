package com.nutricatch.dev.views.navigation.home

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.PostRepository

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    val latestPosts = repository.getLatestPost()

}