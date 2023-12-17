package com.nutricatch.dev.views.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.FragmentHomeBinding
import com.nutricatch.dev.views.factory.HomeViewModelFactory
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            Injection.provideUserRepository(requireContext()),
            preferences
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

        val aaChartView = binding.chart
        val label: Array<String> = arrayOf("a", "b", "c", "d", "e", "f", "g")
        val chartData: Array<Any> = arrayOf(2000, 1950, 2050, 1850, 1880, 2110, 2202)
        val aaChartModel: AAChartModel = AAChartModel()
            //nentuin jenis chart, ada banyak macem
            .chartType(AAChartType.Spline)
            //title, ilangin title biar muncul lebih besar
            .title("")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            //warna background
            .backgroundColor("")
            //warna tulisan dan garis
            .axesTextColor("#000000")
            //untuk ilangin title dari y
            .yAxisTitle("")
            .gradientColorEnable(true)
            //untuk nama datral variablex
            .categories(label)
            //untuk ketebalan garis
            .yAxisLineWidth(2)
            //data
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("")
                        .dataLabels(null)
                        .data(chartData)
                        .color("#4b2b7f")
                )
            )
        //drawing AAchart
        aaChartView.aa_drawChartWithChartModel(aaChartModel)


        /// nanti, ubah jadi observe ke viewmodel
        val user = "John Doe"
        binding.tvUserName.text = getString(com.nutricatch.dev.R.string.home_greeting, user)

        val navController = findNavController()
        viewModel.token.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.headerUser.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToMustLoginDialogFragment()
                    navController.navigate(action)
                }
            } else {
                binding.headerUser.setOnClickListener(Navigation.createNavigateOnClickListener(com.nutricatch.dev.R.id.action_navigation_home_to_navigation_setting))

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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}