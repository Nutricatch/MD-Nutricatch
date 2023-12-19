package com.nutricatch.dev.views.navigation.food_recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.nutricatch.dev.data.api.response.RestaurantResponseItem
import com.nutricatch.dev.data.repository.RestaurantRepository

class RestaurantViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {

    private var _nearbyRestaurants = MutableLiveData<List<RestaurantResponseItem>>()
    val nearbyRestaurants: LiveData<List<RestaurantResponseItem>> = _nearbyRestaurants

    private var _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> = _userLocation

    fun getNearbyRestaurants(lat: Double, lng: Double) =
        restaurantRepository.getNearbyRestaurants(lat, lng)

    fun setNearbyRestaurants(restaurants: List<RestaurantResponseItem>) {
        _nearbyRestaurants.value = restaurants
    }

    fun setUserLocation(latLng: LatLng) {
        _userLocation.value = latLng
    }
}