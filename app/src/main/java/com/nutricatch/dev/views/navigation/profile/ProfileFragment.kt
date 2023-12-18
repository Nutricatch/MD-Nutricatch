package com.nutricatch.dev.views.navigation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.FragmentProfileBinding
import com.nutricatch.dev.utils.Theme
import com.nutricatch.dev.views.factory.UserProfileViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<ProfileViewModel> {
        UserProfileViewModelFactory.getInstance(preferences, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        preferences = Preferences.getInstance(requireContext().dataStore)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.theme.collect { theme ->
                Log.d("TAG", "onViewCreated: Theme changed $theme")
                when (theme) {
                    Theme.Dark -> {
                        binding.swTheme.isChecked = true
                    }

                    Theme.Light -> {
                        binding.swTheme.isChecked = false
                    }
                }
            }
        }

        viewModel.userProfile.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    /// TODO Show Loading
                }

                is ResultState.Success -> {
                    val user = result.data
                    with(binding) {
                        tvName.text = user.username
                        tvEmail.text = user.email
                    }
                }

                is ResultState.Error -> {
                    /// TODO Handle error here
                    if (result.errorCode == 401) {
                        /// TODO navigate ke login page
                    } else {
                        /// TODO tampilkan error dengan toast
                        Toast.makeText(context, "${result.error.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tileMyWeight.setOnClickListener(this)

        binding.tileLanguages.setOnClickListener(this)

        binding.tileShare.setOnClickListener(this)

        binding.swTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isChecked)
        }

        binding.tileContact.setOnClickListener(this)

        binding.tileHelp.setOnClickListener(this)

        binding.tilePrivacy.setOnClickListener(this)

        binding.btnLogout.setOnClickListener(this)

    }

    private fun changeTheme(isChecked: Boolean) {
        if (isChecked) {
            viewModel.setTheme(Theme.Dark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.swTheme.isChecked = true
        } else {
            viewModel.setTheme(Theme.Light)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.swTheme.isChecked = false
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnLogout -> {
                viewModel.logout()
                findNavController().navigate(
                    ProfileFragmentDirections.actionNavigationProfileToAppCheckActivity()
                )
            }

            binding.tileMyWeight -> {
                findNavController().navigate(
                    R.id.action_navigation_profile_to_bodyDetailFragment
                )
            }

            binding.tileLanguages -> {
                findNavController().navigate(
                    R.id.action_navigation_profile_to_languageFragment
                )
            }

            binding.tileShare -> {
                /// TODO implement share with intent explicit
            }

            binding.tileContact -> {
                /// TODO implement intent to email
            }

            binding.tileHelp -> {
                /// TODO implement navigate to help page
            }

            binding.tilePrivacy -> {
                findNavController().navigate(
                    R.id.action_navigation_profile_to_privacyFragment
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}