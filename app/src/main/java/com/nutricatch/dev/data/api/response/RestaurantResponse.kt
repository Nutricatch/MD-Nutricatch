package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(

    @field:SerializedName("RestaurantResponse")
    val restaurantResponse: List<RestaurantResponseItem?> = ArrayList()
)

data class Location(

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)

data class DisplayName(

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("languageCode")
    val languageCode: String? = null
)

data class RestaurantResponseItem(

    @field:SerializedName("displayName")
    val displayName: DisplayName? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("imgUrl")
    val imgUrl: String? = "https://thumbor.forbes.com/thumbor/fit-in/900x510/https://www.forbes.com/health/wp-content/uploads/2022/01/foods_to_eat_to_lose_weight.jpeg",

    @field:SerializedName("id")
    val id: String? = null
)
