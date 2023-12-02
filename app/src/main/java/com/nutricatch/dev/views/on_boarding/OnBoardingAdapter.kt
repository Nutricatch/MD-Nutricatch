package com.nutricatch.dev.views.on_boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nutricatch.dev.databinding.OnBoardingPageItemBinding
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage

class OnBoardingAdapter(private val onBoardingPageList: Array<OnBoardingPage> = OnBoardingPage.entries.toTypedArray()) :
    RecyclerView.Adapter<OnBoardingAdapter.PagerViewHolder>() {
    /** PagerViewHolder viewHolder inner class
     * @param binding is OnboardingPageItemBinding to bind data */
    inner class PagerViewHolder(private val binding: OnBoardingPageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * @param onBoardingPage is OnBoardingPage item
         * bind view **/
        fun bind(onBoardingPage: OnBoardingPage) {
            val res = binding.root.context.resources
            binding.tvBoardHeadline1.text = res.getString(onBoardingPage.titleResource)
            binding.tvBoardDesc1.text = res.getString(onBoardingPage.descriptionResource)
            binding.imageView.setImageResource(onBoardingPage.imgResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            OnBoardingPageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])
    }


}