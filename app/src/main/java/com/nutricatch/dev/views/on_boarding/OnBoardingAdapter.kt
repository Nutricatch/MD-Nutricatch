package com.nutricatch.dev.views.on_boarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(activity: OnBoardingActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingFragment1()
            1 -> OnBoardingFragment2()
            2 -> OnBoardingFragment3()
            else -> throw IndexOutOfBoundsException("Invalid fragment position: $position")
        }
    }
}