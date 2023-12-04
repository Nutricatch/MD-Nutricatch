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
import com.nutricatch.dev.views.auth.LoginActivity
import com.nutricatch.dev.views.navigation.HomeActivity
import com.nutricatch.dev.views.on_boarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<LoadingViewModel> {
        ViewModelFactory(preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preferences.getInstance(applicationContext.dataStore)

        viewModel.isOnBoard().asLiveData().observe(this) { isOnBoard ->
            if (isOnBoard) {
                viewModel.getToken().asLiveData().observe(this) { token ->
                    /// TODO pastikan logika ini benar untuk memeriksa login status
                    if (token != null) {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
            } else {
                val intent = Intent(this, OnBoardingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}