package com.nutricatch.dev.views.navigation.daily_calories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentDailyCaloriesBinding
import com.nutricatch.dev.views.factory.DailyCaloriesViewModelFactory

class DailyCaloriesFragment : Fragment() {

    private var _binding: FragmentDailyCaloriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        binding.caloriesProgress.progress = 60

        viewModel.recommendedRepository.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showLoading(false)
                    val recommendedNutrition = result.data
                    with(binding)
                    {
                        tvGoals.text = recommendedNutrition.calories.toString()
                    }
                }

                is ResultState.Error -> {
                    showLoading(false)
                    /// TODO Handle error here
                    if (result.errorCode == 401) {
                        //
                    } else {
                        /// TODO tampilkan error dengan toast
                        Toast.makeText(context, "${result.error.toString()}", Toast.LENGTH_SHORT)
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