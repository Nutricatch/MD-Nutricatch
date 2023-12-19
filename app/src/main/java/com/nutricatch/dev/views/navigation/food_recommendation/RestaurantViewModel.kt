package com.nutricatch.dev.views.navigation.food_recommendation

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.RestaurantRepository

class RestaurantViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {
    fun getNearbyRestaurants(lat: Double, lng: Double) =
        restaurantRepository.getNearbyRestaurants(lat, lng)
}