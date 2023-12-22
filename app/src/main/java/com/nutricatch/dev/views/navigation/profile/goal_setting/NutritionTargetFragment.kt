package com.nutricatch.dev.views.navigation.profile.goal_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.FragmentNutritionTargetBinding
import com.nutricatch.dev.utils.showToast
import com.nutricatch.dev.views.factory.HomeViewModelFactory
import com.nutricatch.dev.views.factory.UserProfileViewModelFactory
import com.nutricatch.dev.views.navigation.home.HomeViewModel
import com.nutricatch.dev.views.navigation.profile.ProfileViewModel

class NutritionTargetFragment : Fragment() {
    private var _binding: FragmentNutritionTargetBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<ProfileViewModel> {
        UserProfileViewModelFactory.getInstance(
            preferences,
            requireContext()
        )
    }

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(
            requireContext(),
            preferences
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutritionTargetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = Preferences.getInstance(requireContext().applicationContext.dataStore)

        homeViewModel.getRecommendedNutrition().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
//                    showLoading(true)
                }

                is ResultState.Success -> {
//                    showLoading(false)
                    binding.carbsTv.text = result.data.carbohydrates
                    binding.proteinsTv.text = result.data.protein
                    binding.caloriesTv.text = result.data.calories
                    binding.fatsTv.text = result.data.fats
                    binding.fiberTv.text = result.data.minFiber.toString()
                    binding.sodiumTv.text = result.data.maxSodium.toString()
                    binding.sugarTv.text = result.data.maxSugar.toString()
                }

                is ResultState.Error -> {
//                    showLoading(false)
                    if (result.errorCode != 401) {
                        showToast(requireContext(), result.error)
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            findNavController().navigate(NutritionTargetFragmentDirections.actionNutritionTargetFragmentToNavigationHome())
        }
    }
}