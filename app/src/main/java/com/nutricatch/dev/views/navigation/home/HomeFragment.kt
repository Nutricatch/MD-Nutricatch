package com.nutricatch.dev.views.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentHomeBinding
import com.nutricatch.dev.views.factory.HomeViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        binding.tvUserName.text = getString(R.string.home_greeting, user)

        binding.headerUser.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_setting))

        viewModel.foods.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    // showLoading(false)
                    val foods = result.data.foods
                    val adapter = FoodsAdapter()
                    adapter.submitList(foods)
                    binding.rvFoodRecomm.adapter = adapter
                    binding.rvFoodRecomm.layoutManager = GridLayoutManager(requireContext(), 2)
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

        viewModel.recipes.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    //showLoading(false)
                    val recipes = result.data.recipes
                    val adapter = RecipeAdapter()
                    adapter.submitList(recipes)
                    binding.rvRecipeRecomm.adapter = adapter
                    binding.rvRecipeRecomm.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                is ResultState.Loading -> {
//                    showLoading(true)
                }

                is ResultState.Error -> {
//                    showLoading(false)

                }
            }
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