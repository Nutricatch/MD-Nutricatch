package com.nutricatch.dev.views.navigation.dailyCalories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.FragmentDailyCaloriesBinding
import com.nutricatch.dev.databinding.FragmentHomeBinding

class DailyCaloriesFragment : Fragment() {
    private var _binding: FragmentDailyCaloriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDailyCaloriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todayCalories = 1120
        val goals = 2250
        //TODO : Inisialisasi jumlah kalori dan progress bar ketika get kalori Harian
        binding.tvCalories.text = todayCalories.toString()
        binding.tvGoals.text = goals.toString()
        binding.caloriesPercentage.progress = 48
    }

    companion object {

    }
}