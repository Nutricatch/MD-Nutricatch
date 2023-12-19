package com.nutricatch.dev.views.navigation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nutricatch.dev.databinding.SliderItemBinding
import com.smarteist.autoimageslider.SliderViewAdapter

private val imagesUrl = arrayOf(
    "https://drive.google.com/uc?id=1ZphjChHCUgaFqYmETr9bIhRraGQhK315&export=download",
    "https://drive.google.com/uc?id=1WzBDOYi-OyHPk1garLIGjn9_DAdAGeyT&export=download"
)

class RecommendationSliderAdapter(private val onClick: (position: Int) -> Unit) :
    SliderViewAdapter<RecommendationSliderAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: SliderItemBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.root.context).load(imageUrl).into(binding.img)
        }
    }

    override fun getCount(): Int = imagesUrl.size

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val binding = SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val image = imagesUrl[position]
        viewHolder.bind(image)
        viewHolder.itemView.setOnClickListener {
            onClick(position)
        }
    }
}