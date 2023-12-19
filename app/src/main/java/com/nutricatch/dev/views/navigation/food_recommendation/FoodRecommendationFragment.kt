package com.nutricatch.dev.views.navigation.food_recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentNearbyRestaurantsBinding
import com.nutricatch.dev.utils.showToast
import com.nutricatch.dev.views.factory.RestaurantViewModelFactory

class FoodRecommendationFragment : Fragment() {

    private var _binding: FragmentNearbyRestaurantsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RestaurantViewModel> {
        RestaurantViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNearbyRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NearbyRestaurantAdapter()
        binding.rvNearbyRestaurants.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvNearbyRestaurants.layoutManager = layoutManager

        // TODO get lat from provided gps
        val lat = -6.399702569134995
        val lng = 106.9937287941783

        viewModel.getNearbyRestaurants(lat, lng).observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {
                    showToast(requireContext(), "Getting Data")
                }

                is ResultState.Success -> {
                    showToast(requireContext(), "Success getting data")
                    val restaurants = it.data
                    adapter.submitList(restaurants)
                    showToast(requireContext(), "Success getting data2")
                }

                is ResultState.Error -> {
                    showToast(requireContext(), "Error")
                }

                else -> {

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}