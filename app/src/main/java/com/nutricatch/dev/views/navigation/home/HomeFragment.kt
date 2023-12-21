package com.nutricatch.dev.views.navigation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.DiamondHomeDialogBinding
import com.nutricatch.dev.databinding.FragmentHomeBinding
import com.nutricatch.dev.utils.showToast
import com.nutricatch.dev.views.factory.HomeViewModelFactory
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var _dialogBinding: DiamondHomeDialogBinding? = null
    private val dialogBinding get() = _dialogBinding!!

    private lateinit var preferences: Preferences
    private lateinit var navController: NavController

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            Injection.provideUserRepository(requireContext()),
            preferences,
            Injection.provideDailyIntakeRepository(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferences = Preferences.getInstance(requireContext().applicationContext.dataStore)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel.getRecommendedNutrition().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showLoading(false)
                    viewModel.setRecommendedNutrition(result.data)
                }

                is ResultState.Error -> {
                    showLoading(false)
                    if (result.errorCode != 401) {
                        showToast(requireContext(), result.error)
                    }
                }
            }
        }

        getSummary()

        viewModel.todayConsumes.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    //TODO Masukin data data di bawah ke dalam chart
                    showLoading(false)
                    viewModel.setTodayConsume(result.data)
                    getSummary()
                }

                is ResultState.Error -> {
                    showLoading(false)
                    if (result.errorCode != 401) {
                        showToast(requireContext(), result.error)
                    }
                }
            }
        }

        binding.vGroupDiamond.setOnClickListener {
            /// TODO show dialog to buy a diamond, if less than 5, show ads button
            showDiamondDialog()
        }

        val navController = findNavController()

        viewModel.diamonds.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    binding.tvDiamondCount.text = "${result.data}"
                }

                is ResultState.Loading -> {
                    // showLoading(true)
                }

                is ResultState.Error -> {
                    // showLoading(false)
                    // handle error
                }
            }
        }

        viewModel.token.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.headerUser.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToMustLoginDialogFragment()
                    navController.navigate(action)
                }
            } else {
                binding.headerUser.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_setting))

            }
        }

        viewModel.userData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    binding.tvUserName.text = result.data.username
                }

                is ResultState.Loading -> {
                    // showLoading(true)
                }

                is ResultState.Error -> {
                    // showLoading(false)
                    // handle error
                }
            }
        }

        val sliderView = binding.imageSlider
        sliderView.setSliderAdapter(RecommendationSliderAdapter(::onClickListener))
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.scrollTimeInSec = 2
        sliderView.startAutoCycle()
    }

    private fun showDiamondDialog() {
        _dialogBinding = DiamondHomeDialogBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()

        dialogBinding.btnAds.setOnClickListener {

        }
        dialogBinding.btnSubscribe.setOnClickListener {
            dialog.hide()
            navController.navigate(HomeFragmentDirections.actionNavigationHomeToPaymentWebViewFragment())
        }

    }

    private fun getSummary() {
        viewModel.todayResult.observe(viewLifecycleOwner) { recommended ->
            Log.d("TAG", "onViewCreated: home $recommended")

            var carbPercentage = recommended.carbohydrates ?: 0.0
            var fatPercentage = recommended.fat ?: 0.0
            var proteinPercentage = recommended.protein ?: 0.0

            val carbText = (carbPercentage * 100).toInt().toString()
            val fatText = (fatPercentage * 100).toInt().toString()
            val proteinText = (proteinPercentage * 100).toInt().toString()

            if (carbPercentage <= 0.07) {
                carbPercentage = 0.07
            }
            if (fatPercentage <= 0.07) {
                fatPercentage = 0.07
            }
            if (proteinPercentage <= 0.07) {
                proteinPercentage = 0.07
            }

            Log.d("TAG", "onViewCreated: home $carbPercentage $fatPercentage $proteinPercentage")

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.vCarbs)
            constraintSet.constrainPercentHeight(binding.vFgCarbs.id, carbPercentage.toFloat())
            constraintSet.applyTo(binding.vCarbs)

            binding.tvCarbsPercents.text = getString(R.string.percentage, carbText)
            binding.tvFatPercents.text = getString(R.string.percentage, fatText)
            binding.tvProteinPercents.text = getString(R.string.percentage, proteinText)

            val constraintSetFat = ConstraintSet()
            constraintSetFat.clone(binding.vFats)
            constraintSetFat.constrainPercentHeight(binding.vFgFats.id, fatPercentage.toFloat())
            constraintSetFat.applyTo(binding.vFats)

            val constraintSetProtein = ConstraintSet()
            constraintSetProtein.clone(binding.vProteins)
            constraintSetProtein.constrainPercentHeight(
                binding.vFgProteins.id,
                proteinPercentage.toFloat()
            )
            constraintSetProtein.applyTo(binding.vProteins)
        }
    }

    private fun onClickListener(position: Int) {
        if (position == 0) {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNavigationRecipe())
        } else {
            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToFoodRecommendationFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _dialogBinding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}