package com.nutricatch.dev.views.navigation.profile.goal_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nutricatch.dev.R
import com.nutricatch.dev.data.api.response.ActivityLevel
import com.nutricatch.dev.databinding.FragmentUserGoalBinding

class UserGoalFragment : Fragment() {
    private var _binding: FragmentUserGoalBinding? = null
    private val binding get() = _binding!!

    private lateinit var activityLevel: ActivityLevel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load("https://drive.google.com/uc?id=1nEkB6EGWIGJQVSJNYtcB1P3qlwOnBXTI&export=download")
            .into(binding.imgGoal)

        setupActivityLevel()
        binding.rbFat.isChecked = true
        binding.btnNext.setOnClickListener {
            /// TODO call to api

            findNavController().navigate(UserGoalFragmentDirections.actionUserGoalFragmentToBodyDetailFragment())
        }

    }

    private fun setupActivityLevel() {
        val data = listOf(
            ActivityLevel.SEDENTARY.label,
            ActivityLevel.MODERATELY_ACTIVE.label,
            ActivityLevel.VERY_ACTIVE.label
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerGoal.adapter = adapter

        binding.spinnerGoal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle selected item
                val selectedItem = parent?.getItemAtPosition(position).toString()
                activityLevel = ActivityLevel.entries.find { it.label == selectedItem }
                    ?: ActivityLevel.SEDENTARY
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}