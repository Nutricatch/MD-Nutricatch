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
        binding.rvLatestPost.layoutManager = layoutManager

//        //ini demo untuk chart
//        val chartData = mapOf(
//            "Senin" to 1960.0,
//            "Selasa" to 1950.0,
//            "Rabu" to 1840.0,
//            "Kamis" to 1870.0,
//            "Jumat" to 1910.0,
//            "Sabtu" to 1920.0,
//            "Minggu" to 2010.0
//        )
//
//        val coreData = ChartData().addDataset(ChartNumberDataset().data(chartData.values.toList())
//            .offset(1000)).labels(chartData.keys.toList())
//
//        val chartOptions = ChartOptions()
//            .plugin(
//                Plugin()
//                    .subtitle(
//                        Title()
//                            .display(true)
//                            .text("Subtitle example")
//                            .position(Position.BOTTOM)
//                    ).title(
//                        Title()
//                            .display(true)
//                            .text("Title")
//                            .position(Position.TOP)
//                            .align(TextAlign.CENTER)
//                            .color("red")
//                    )
//                    .tooltip(
//                        Tooltip(false)
//                    )
//
//            )
//            .elements(
//                Elements()
//                    .line(
//                        Line()
//                            .tension(0.5f)
//                    )
//            )
//            .scales(
//                Scales(x = Axis(min = 0.00), y = Axis(max = 2500.00))
//            )
//
//
//        val chartModel = ChartCoreModel()
//            .type(ChartTypes.BAR)
//            .data(coreData)
//            .options(chartOptions)
//
//        binding.chartCore.draw(chartModel)

        val aaChartView = binding.chart

        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("Calories Tracker")
            .backgroundColor("#4b2b7f")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            .series(arrayOf(
                AASeriesElement()
                    .name("")
                    .data(arrayOf(2000, 1950, 2050, 1850, 1880, 2110, 2102, 2113, 2231, 1820, 1922, 1966))
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
                    binding.rvLatestPost.adapter = adapter
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