package com.nutricatch.dev.views.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.chartcore.common.ChartTypes
import com.github.chartcore.common.Position
import com.github.chartcore.common.TextAlign
import com.github.chartcore.data.chart.ChartCoreModel
import com.github.chartcore.data.chart.ChartData
import com.github.chartcore.data.dataset.ChartNumberDataset
import com.github.chartcore.data.option.ChartOptions
import com.github.chartcore.data.option.elements.Elements
import com.github.chartcore.data.option.elements.Line
import com.github.chartcore.data.option.plugin.Plugin
import com.github.chartcore.data.option.plugin.Title
import com.github.chartcore.data.option.plugin.Tooltip
import com.github.chartcore.data.option.scale.Axis
import com.github.chartcore.data.option.scale.Scales
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentHomeBinding
import com.nutricatch.dev.views.factory.MainViewModelFactory
import kotlin.math.min

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
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
        val layoutManager = LinearLayoutManager(requireContext())


        val aaChartView = binding.chart
        val label: Array<String> = arrayOf("a","b","c","d","e","f","g")
        val chartData: Array<Any> = arrayOf(2000, 1950, 2050, 1850, 1880, 2110, 2202)
        val aaChartModel : AAChartModel = AAChartModel()
            //nentuin jenis chart, ada banyak macem
            .chartType(AAChartType.Line)
            //title, ilangin title biar muncul lebih besar
            .title("")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            //warna background
            .backgroundColor("")
            //warna tulisan dan garis
            .axesTextColor("FFFFFF")
            //untuk ilangin title dari y
            .yAxisTitle("")
            .gradientColorEnable(true)
            //untuk nama datral variablex
            .categories(label)
            //untuk ketebalan garis
            .yAxisLineWidth(2)
            //data
            .series(arrayOf(
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

        viewModel.latestPosts.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    //showLoading(false)
                    val posts = result.data.latestPosts
                    val adapter = LatestPostAdapter()
                    adapter.submitList(posts)
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
}