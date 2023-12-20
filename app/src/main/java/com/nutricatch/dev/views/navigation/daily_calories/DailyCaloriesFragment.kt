package com.nutricatch.dev.views.navigation.daily_calories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentDailyCaloriesBinding
import com.nutricatch.dev.views.factory.DailyCaloriesViewModelFactory

class DailyCaloriesFragment : Fragment() {

    private var _binding: FragmentDailyCaloriesBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<DailyCaloriesViewModel> {
        DailyCaloriesViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyCaloriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /// TODO later, update this goal
        val goal = 2250
        binding.caloriesProgress.progress = 10

        val adapter = DailyCaloriesAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = layoutManager

        viewModel.dailyData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showLoading(false)
                    var cal = 0

                    for (food in result.data) {
                        cal += food.calories?.toInt() ?: 0
                    }

                    binding.tvCalories.text = cal.toString()
                    binding.caloriesProgress.progress = cal * 100 / goal
                    adapter.submitList(result.data)

                }

                is ResultState.Error -> {
                    showLoading(false)
                    /// TODO Handle error here
                    if (result.errorCode == 401) {
                        //
                    } else {
                        /// TODO tampilkan error dengan toast
                        Toast.makeText(context, result.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        //TODO Handle Warning kalau lebih kalori jika udah dapet fungsi
//        if ()
//        {
//            binding.warningTv.visibility = View.VISIBLE
//        }
//        else if ()
//        {
//            binding.warningTv.visibility = View.INVISIBLE
//        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}