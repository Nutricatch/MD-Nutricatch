package com.nutricatch.dev.views.navigation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nutricatch.dev.databinding.SettingItemBinding
import com.nutricatch.dev.model.SettingMenu

class SettingMenuAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<SettingMenu, SettingMenuAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(val binding: SettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(settingMenu: SettingMenu) {
            binding.imgSettingIcon.setImageResource(settingMenu.drawableIcon)
            binding.tvSettingTitle.text = settingMenu.title

            itemView.setOnClickListener {
                onClick(settingMenu.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SettingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val settingMenu = getItem(position)
        holder.bind(settingMenu)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SettingMenu>() {
            override fun areItemsTheSame(oldItem: SettingMenu, newItem: SettingMenu): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SettingMenu, newItem: SettingMenu): Boolean {
                return oldItem == newItem
            }

        }
    }
}