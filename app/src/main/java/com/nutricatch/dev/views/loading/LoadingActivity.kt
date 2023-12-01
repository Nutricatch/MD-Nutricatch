package com.nutricatch.dev.views.loading

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.ActivityLoadingBinding
import com.nutricatch.dev.views.ViewModelFactory
import com.nutricatch.dev.views.on_boarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding
    private val preferences = Preferences.getInstance(applicationContext.dataStore)
    private val viewModel by viewModels<LoadingViewModel> {
        ViewModelFactory(preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isOnBoard().asLiveData().observe(this) { isOnBoard ->
            if (isOnBoard) {
//                startActivity(Intent(this, Logina))
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
        }
    }
}