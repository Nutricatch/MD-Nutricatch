package com.nutricatch.dev.views.on_boarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nutricatch.dev.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dotIndicator = binding.dotsIndicator
        val viewPager =binding.viewPager2
        val adapter = OnBoardingAdapter(this)
        viewPager.adapter = adapter
        dotIndicator.attachTo(viewPager)
    }
}