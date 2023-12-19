package com.nutricatch.dev.views.navigation.food_recommendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nutricatch.dev.data.api.response.RestaurantResponseItem
import com.nutricatch.dev.databinding.RecommendationItemBinding

class NearbyRestaurantAdapter :
    ListAdapter<RestaurantResponseItem, NearbyRestaurantAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RecommendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)
    }

    class ViewHolder(val binding: RecommendationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: RestaurantResponseItem) {
            binding.tvTitle.text = restaurant.displayName?.text
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RestaurantResponseItem>() {
            override fun areItemsTheSame(
                oldItem: RestaurantResponseItem,
                newItem: RestaurantResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RestaurantResponseItem,
                newItem: RestaurantResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
