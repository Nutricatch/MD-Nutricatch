package com.nutricatch.dev.views.navigation.food_recommendation

import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentNearbyRestaurantsBinding
import com.nutricatch.dev.utils.Const
import com.nutricatch.dev.utils.showToast
import com.nutricatch.dev.views.factory.RestaurantViewModelFactory
import java.util.concurrent.TimeUnit

class FoodRecommendationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentNearbyRestaurantsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<RestaurantViewModel> {
        RestaurantViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Const.FINE_LOCATION_PERMISSION] ?: false -> {
                getMyLocation()
            }

            permissions[Const.COARSE_LOCATION_PERMISSION] ?: false -> {
                getMyLocation()
            }
        }
    }

    private val resolutionLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK ->
                    Log.i(
                        "New Story Activity",
                        "onActivityResult: All location settings are satisfied."
                    )

                AppCompatActivity.RESULT_CANCELED ->
                    showToast(requireContext(), getString(R.string.must_activate_gps))
                /// TODO mungkin nanti bisa update ui, menampilkan harus ijinkan lokasi, sama kasih tombol buat minta ijin lagi
            }
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

        requestLocationPermission()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        getMyLocation()

        binding.fabMapView.setOnClickListener(this)

        val adapter = NearbyRestaurantAdapter()
        binding.rvNearbyRestaurants.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvNearbyRestaurants.layoutManager = layoutManager

        viewModel.nearbyRestaurants.observe(viewLifecycleOwner) {
            val restaurants = it
            adapter.submitList(restaurants)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.fabMapView -> {
                val action =
                    FoodRecommendationFragmentDirections.actionFoodRecommendationFragmentToMapsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun getMyLocation() {
        if (checkPermission(Const.FINE_LOCATION_PERMISSION) && checkPermission(Const.COARSE_LOCATION_PERMISSION)) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        showToast(requireContext(), getString(R.string.cannot_get_location))
                    else {
                        showToast(requireContext(), getString(R.string.location_retrieved))
                        /// TOOD hit api di sini karena sudah dapat lat long nya

                        val lat = location.latitude
                        val lng = location.longitude

                        viewModel.setUserLocation(LatLng(lat, lng))

                        viewModel.getNearbyRestaurants(lat, lng).observe(viewLifecycleOwner) {
                            when (val result = it) {
                                is ResultState.Loading -> {
                                    showToast(requireContext(), "Getting Data")
                                }

                                is ResultState.Success -> {
                                    viewModel.setNearbyRestaurants(result.data)
                                }

                                is ResultState.Error -> {
                                    showToast(requireContext(), "Error")
                                }
                            }
                        }
                    }
                }

        } else {
            requestPermissionLauncher.launch(arrayOf(Const.FINE_LOCATION_PERMISSION))
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationRequest = LocationRequest.Builder(TimeUnit.SECONDS.toMillis(1))
            .setMaxUpdateDelayMillis(TimeUnit.SECONDS.toMillis(1))
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(requireContext())
        client.checkLocationSettings(builder.build())
            .addOnSuccessListener {
                showToast(
                    requireContext(),
                    getString(R.string.getting_your_location)
                )
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        resolutionLauncher.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        showToast(requireContext(), sendEx.message.toString())
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
