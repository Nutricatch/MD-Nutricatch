package com.nutricatch.dev.views.navigation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nutricatch.dev.databinding.ItemLatestPostBinding
import com.nutricatch.dev.model.LatestPost

class LatestPostAdapter : ListAdapter<LatestPost, LatestPostAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestPostAdapter.ViewHolder {
        val binding =
            ItemLatestPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemLatestPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(latestPost: LatestPost) {
            binding.tvLatestTitle.text = latestPost.title
            binding.tvDescPost.text = latestPost.description
            Glide.with(binding.root.context).load(latestPost.imgUrl).into(binding.imgLatestPost)
        }
    }

    override fun onBindViewHolder(holder: LatestPostAdapter.ViewHolder, position: Int) {
        val latestPost = getItem(position)
        holder.bind(latestPost)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LatestPost>() {
            override fun areItemsTheSame(oldItem: LatestPost, newItem: LatestPost): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LatestPost, newItem: LatestPost): Boolean {
                return oldItem == newItem
            }

        }
    }
}