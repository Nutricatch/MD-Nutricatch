package com.nutricatch.dev.views.navigation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nutricatch.dev.databinding.RecommendationItemBinding
import com.nutricatch.dev.model.Food

class FoodsAdapter : ListAdapter<Food, FoodsAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RecommendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: RecommendationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.tvName.text = food.name
            binding.tvCalories.text = food.caloric
            Glide.with(binding.root.context).load(food.imgUrl).into(binding.imgRecommendation)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem == newItem
            }

        }
    }
}