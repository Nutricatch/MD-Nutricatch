package com.nutricatch.dev.views.on_boarding.customView

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.nutricatch.dev.animation.setParallaxTransformation
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.OnBoardingViewBinding
import com.nutricatch.dev.views.navigation.HomeActivity
import com.nutricatch.dev.views.on_boarding.OnBoardingAdapter
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage
import kotlinx.coroutines.launch

class OnBoardingView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val numberOfPages by lazy { OnBoardingPage.entries.size }
    private val preferences = Preferences.getInstance(context.dataStore)


    init {
        val binding = OnBoardingViewBinding.inflate(LayoutInflater.from(context), this, true)
        with(binding) {
            setUpSlider()
            addingButtonsClickListeners()
        }

    }

    private fun OnBoardingViewBinding.setUpSlider() {
        with(viewPager2) {
            adapter = OnBoardingAdapter()

            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }
//
//            setPageTransformer(pageCompositePageTransformer)

            addSlideChangeListener()

            val wormDotsIndicator = pageIndicator
            wormDotsIndicator.attachTo(this)
        }
    }


    private fun OnBoardingViewBinding.addSlideChangeListener() {

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (numberOfPages > 1) {
                    val newProgress = (position + positionOffset) / (numberOfPages - 1)
                    onboardingRoot.progress = newProgress
                }
            }
        })
    }

    private fun OnBoardingViewBinding.addingButtonsClickListeners() {
        nextBtn.setOnClickListener { navigateToNextSlide(viewPager2) }
        skipBtn.setOnClickListener {
            setFirstTimeLaunchToFalse()
            navigateToHome()
        }
        startBtn.setOnClickListener {
            setFirstTimeLaunchToFalse()
            navigateToHome()
        }
    }

    private fun setFirstTimeLaunchToFalse() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            preferences.boarding()
        }
    }

    private fun navigateToNextSlide(slider: ViewPager2?) {
        val nextSlidePos: Int = slider?.currentItem?.plus(1) ?: 0
        slider?.setCurrentItem(nextSlidePos, true)
    }

    private fun navigateToHome() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}