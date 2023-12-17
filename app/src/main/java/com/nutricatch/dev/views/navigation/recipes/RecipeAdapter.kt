package com.nutricatch.dev.views.navigation.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nutricatch.dev.databinding.RecommendationItemBinding
import com.nutricatch.dev.model.Recipe

class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecommendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    class ViewHolder(val binding: RecommendationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.tvName.text = recipe.title
            binding.tvCalories.text = recipe.caloric
            Glide.with(binding.root.context).load(recipe.imgUrl).into(binding.imgRecommendation)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

        }
    }
}