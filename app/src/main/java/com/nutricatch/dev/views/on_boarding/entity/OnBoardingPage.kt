package com.omni.onboardingscreen.feature.onboarding.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nutricatch.dev.R

enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int,
    @DrawableRes val imgResource: Int
) {

    ONE(R.string.onboard_headline_1, R.string.onboard_body_1, R.drawable.ic_directions),
    TWO(R.string.onboard_headline_2, R.string.onboard_body_2, R.drawable.ic_hang_out),
    THREE(R.string.onboard_headline_3, R.string.onboard_body_3, R.drawable.ic_a_day_at_the_park)
}