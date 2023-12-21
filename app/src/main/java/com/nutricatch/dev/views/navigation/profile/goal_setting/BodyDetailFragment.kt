package com.nutricatch.dev.views.navigation.profile.goal_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.response.ActivityLevel
import com.nutricatch.dev.data.api.response.FitnessGoal
import com.nutricatch.dev.data.api.response.Gender
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.FragmentBodyDetailBinding
import com.nutricatch.dev.utils.showToast
import com.nutricatch.dev.views.factory.UserProfileViewModelFactory
import com.nutricatch.dev.views.navigation.profile.ProfileViewModel

class BodyDetailFragment : Fragment() {
    private var _binding: FragmentBodyDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<ProfileViewModel> {
        UserProfileViewModelFactory.getInstance(preferences, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBodyDetailBinding.inflate(inflater, container, false)

        preferences = Preferences.getInstance(requireContext().dataStore)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbMale.isChecked = true

        viewModel.userHealthData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showLoading(false)
                    val healthResponse = result.data
                    with(binding) {
                        if (healthResponse.age != null) {
                            edtAge.setText(healthResponse.age.toString())
                        }
                        if (healthResponse.height != null) {
                            edtMyHeight.setText(healthResponse.height.toString())
                        }
                        if (healthResponse.weight != null) {
                            edtMyWeight.setText(healthResponse.weight.toString())
                        }

                        if (healthResponse.gender == Gender.MALE.name) {
                            rbMale.isChecked = true
                        } else if (healthResponse.gender == Gender.FEMALE.name) {
                            rbFemale.isChecked = true
                        }
                    }
                }

                is ResultState.Error -> {
                    showLoading(false)
                    /// TODO Handle error here
                    if (result.errorCode == 401) {
                        //TODO navigate ke login page
//                        findNavController().navigate(BodyDetailFragmentDirections.actionBodyDetailFragmentToLoginFragment2())
                    } else {
                        /// TODO tampilkan error dengan toast
                        Toast.makeText(context, "${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }


        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnUpdate.setOnClickListener {

            val weight: Double = binding.edtMyWeight.text.toString().trim().toDoubleOrNull() ?: 0.0
            val height: Double = binding.edtMyHeight.text.toString().trim().toDoubleOrNull() ?: 0.0
            val age: Double = binding.edtAge.text.toString().trim().toDoubleOrNull() ?: 0.0
            val genderId = binding.rgGender.checkedRadioButtonId
            val gender = if (genderId == binding.rbMale.id) {
                Gender.MALE
            } else {
                Gender.FEMALE
            }

            val fitnessGoal: FitnessGoal = FitnessGoal.WeightGain
            val activityLevel: ActivityLevel = ActivityLevel.SEDENTARY

            binding.toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->

                if (isChecked) {
                    when (checkedId) {
                        R.id.btnWeightLoss -> {
                            val fitnessGoal: FitnessGoal = FitnessGoal.WeightLoss
                        }
                        R.id.btnWeightGain -> {
                            val fitnessGoal: FitnessGoal = FitnessGoal.WeightGain
                        }
                        R.id.btnMaintenance -> {
                            val fitnessGoal: FitnessGoal = FitnessGoal.Maintenance
                        }
                    }
                } else {
                    if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                        Toast.makeText(context, "Please choose your Fitness Goal", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.toggleButtonGroupAcvitivityLevel.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->

                if (isChecked) {
                    when (checkedId) {
                        R.id.btnModeratelyActive -> {
                            val activityLevel: ActivityLevel = ActivityLevel.MODERATELY_ACTIVE
                        }
                        R.id.btnVeryActive -> {
                            val activityLevel: ActivityLevel = ActivityLevel.VERY_ACTIVE
                        }
                        R.id.btnSedentary -> {
                            val activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
                        }
                    }
                } else {
                    if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                        Toast.makeText(context, "Please choose your Activity Level", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            viewModel.updateHealthData(weight, height, age, gender, fitnessGoal, activityLevel)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            /// TODO Show Loading
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showLoading(false)
                            showToast(requireContext(), getString(R.string.success_update_data))
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }

                        is ResultState.Error -> {
                            showLoading(false)
                            /// TODO Handle error here
                            if (result.errorCode == 401) {
                                /// TODO navigate ke login page
//                                findNavController().navigate(BodyDetailFragmentDirections.actionBodyDetailFragmentToLoginFragment2())
                            } else {
                                /// TODO tampilkan error dengan toast
                                Toast.makeText(
                                    context,
                                    "${result.error}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        else -> {}
                    }
                }
        }

        binding.edtMyHeight.addTextChangedListener {
            binding.tvBmi.text = calculateBmi()
        }
        binding.edtMyWeight.addTextChangedListener {
            binding.tvBmi.text = calculateBmi()
        }
    }

    private fun calculateBmi(): String {

        val weight = binding.edtMyWeight.text.toString().toDoubleOrNull()
        val height = binding.edtMyHeight.text.toString().toDoubleOrNull()

        if (weight != null && height != null) {
            val bmi = (weight / (height * height / 10000))
            binding.tvBmiName.text = when {
                bmi < 17 -> {
                    getString(R.string.underweight)
                }

                bmi < 18.5 -> {
                    getString(R.string.slightly_underweight)
                }

                bmi > 30 -> {
                    getString(R.string.extremely_overweight)
                }

                bmi > 27 -> {
                    getString(R.string.overweight)
                }

                else -> {
                    getString(R.string.slightly_overweight)
                }
            }

            return bmi.toString()
        }

        return "0"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}