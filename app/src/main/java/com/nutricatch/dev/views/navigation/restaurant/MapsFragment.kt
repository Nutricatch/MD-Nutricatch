package com.nutricatch.dev.views.navigation.restaurant

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.nutricatch.dev.R
import com.nutricatch.dev.data.api.response.RestaurantResponseItem
import com.nutricatch.dev.databinding.FragmentMapsBinding
import com.nutricatch.dev.utils.Permissions.COARSE_LOCATION_PERMISSION
import com.nutricatch.dev.utils.Permissions.FINE_LOCATION_PERMISSION
import com.nutricatch.dev.views.factory.RestaurantViewModelFactory
import com.nutricatch.dev.views.navigation.food_recommendation.RestaurantViewModel

class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<RestaurantViewModel> {
        RestaurantViewModelFactory.getInstance(requireParentFragment().requireContext().applicationContext)
    }

    private lateinit var mMap: GoogleMap
    private var restaurants: List<RestaurantResponseItem> = listOf()

    private val latLngBoundsBuilder = LatLngBounds.builder()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        getMyLocation()

        addMarkers()
    }

    private fun addMarkers() {
        restaurants.forEach { restaurant ->
            val lat = restaurant.location?.lat ?: 0.0
            val long = restaurant.location?.lng ?: 0.0
            val latLng = LatLng(lat, long)
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("${restaurant.displayName?.text}")
            )
            latLngBoundsBuilder.include(latLng)
        }

        val bounds = latLngBoundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[FINE_LOCATION_PERMISSION] ?: false -> {
                getMyLocation()
            }

            permissions[COARSE_LOCATION_PERMISSION] ?: false -> {
                getMyLocation()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        viewModel.nearbyRestaurants.observe(viewLifecycleOwner) { restaurantsResponse ->
            restaurants = restaurantsResponse
        }
        viewModel.userLocation.observe(viewLifecycleOwner) { latLngBoundsBuilder.include(it) }
    }

    private fun checkPermission(permission: String): Boolean {
        Log.d("TAG", "checkPermission: ")
        return ContextCompat.checkSelfPermission(
            requireContext(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLocation() {
        if (checkPermission(FINE_LOCATION_PERMISSION) && checkPermission(COARSE_LOCATION_PERMISSION)) {
            mMap.isMyLocationEnabled = true

        } else {
            requestPermissionLauncher.launch(arrayOf(FINE_LOCATION_PERMISSION))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}