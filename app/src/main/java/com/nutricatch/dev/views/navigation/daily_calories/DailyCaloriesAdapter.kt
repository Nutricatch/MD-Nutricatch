package com.nutricatch.dev.views.navigation.daily_calories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nutricatch.dev.data.api.response.ConsumeResponse
import com.nutricatch.dev.databinding.TodayCaloriesItemBinding
import com.nutricatch.dev.helper.foodLabelsMap

class DailyCaloriesAdapter:ListAdapter<ConsumeResponse, DailyCaloriesAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder (val binding: TodayCaloriesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: ConsumeResponse) {
            val foodName = foodLabelsMap[history.foodName]
            binding.tvTitle.text = foodName
            binding.tvCalories.text = history.calories.toString()
            binding.tvId.text = history.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodayCaloriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ConsumeResponse>() {
            override fun areItemsTheSame(
                oldItem: ConsumeResponse,
                newItem: ConsumeResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ConsumeResponse,
                newItem: ConsumeResponse
            ): Boolean {
                return oldItem == newItem
            }

        }
    }



}