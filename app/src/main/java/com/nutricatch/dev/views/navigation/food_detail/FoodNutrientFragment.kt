package com.nutricatch.dev.views.navigation.food_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.response.FoodsResponseItem
import com.nutricatch.dev.databinding.FragmentFoodDetailBinding
import com.nutricatch.dev.helper.foodLabelsMap
import com.nutricatch.dev.utils.getRealPathFromUri
import com.nutricatch.dev.utils.showToast
import com.nutricatch.dev.views.factory.FoodNutrientViewModelFactory
import java.io.File

class FoodNutrientFragment : Fragment() {
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FoodNutrientViewModel> {
        FoodNutrientViewModelFactory.getInstance(requireContext())
    }

    private lateinit var nutrient: FoodsResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = FoodNutrientFragmentArgs.fromBundle(arguments as Bundle).imageUri.toUri()
        val isFromGallery = FoodNutrientFragmentArgs.fromBundle(arguments as Bundle).isfromGallery
        val label = FoodNutrientFragmentArgs.fromBundle(arguments as Bundle).label

        binding.imgFood.setImageURI(uri)

        viewModel.getFoodNutrient(label).observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    with(binding) {
                        nutrient = result.data
                        tvName.text = foodLabelsMap[nutrient.name]
                        tvCaloric.text = nutrient.calories.toString()
                        tvProtein.text = nutrient.protein.toString()
                        tvFat.text = nutrient.fat.toString()
                        tvCarb.text = nutrient.carbs.toString()
                        tvSugar.text = nutrient.sugar.toString()
                        tvFiber.text = nutrient.fibers.toString()
                        tvSodium.text = nutrient.sodium.toString()
                    }
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

        binding.btnSave.setOnClickListener {
            if (!isFromGallery) {
                val filePath = getRealPathFromUri(requireContext(), uri)
                if (filePath != null) File(filePath).delete()
            }

            viewModel.saveEating(
                nutrient.calories!!,
                nutrient.carbs!!,
                nutrient.fat!!,
                nutrient.protein!!,
                nutrient.sodium!!,
                nutrient.sugar!!,
                nutrient.fibers!!
            ).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResultState.Loading -> {
//                        showLoading(true)
                    }

                    is ResultState.Success -> {
//                        showLoading(false)
                        findNavController().navigate(FoodNutrientFragmentDirections.actionFoodDetailFragmentToNavigationDailyCalories())
                    }

                    is ResultState.Error -> {
//                        showLoading(false)
                        showToast(
                            requireContext(),
                            "There is something wrong when loading your data"
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}