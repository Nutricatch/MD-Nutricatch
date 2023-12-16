package com.nutricatch.dev.views.navigation.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.FragmentRecipesBinding
import com.nutricatch.dev.views.factory.HomeViewModelFactory

class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    val binding get() = _binding!!
    private lateinit var preferences: Preferences

    private val viewModel by viewModels<RecipeViewModel> {
        HomeViewModelFactory.getInstance(requireContext(), preferences)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferences = Preferences.getInstance(requireContext().applicationContext.dataStore)
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val root = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodayCaloriesEating.layoutManager = layoutManager

        viewModel.recipes.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {}
                is ResultState.Success -> {
                    val recipes = it.data.recipes
                    val adapter = RecipeAdapter()
                    adapter.submitList(recipes)
                    binding.rvTodayCaloriesEating.adapter = adapter
                }

                is ResultState.Error -> {}
                else -> {

                }
            }
        }

        // Inflate the layout for this fragment
        return root
    }
}