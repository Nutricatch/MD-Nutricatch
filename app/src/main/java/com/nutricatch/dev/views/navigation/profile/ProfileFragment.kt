package com.nutricatch.dev.views.navigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter: SettingMenuAdapter by lazy {
        SettingMenuAdapter(::onClickListener)
    }
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tileMyWeight.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_navigation_setting_to_bodyDetailFragment
            )
        }
        binding.tileLanguages.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_navigation_setting_to_languageFragment
            )
        }

        binding.tileShare.setOnClickListener {
            /// TODO implement share with intent explicit
        }

        binding.swTheme.setOnCheckedChangeListener { _, isChecked ->
            changeTheme(isChecked)
        }

        binding.tileContact.setOnClickListener {
            /// TODO implement intent to email
        }

        binding.tileHelp.setOnClickListener {
            /// TODO implement navigate to help page
        }

        binding.tilePrivacy.setOnClickListener {
            /// TODO implement navigate to privacy policy page
        }

    }

    private fun changeTheme(isChecked: Boolean) {
        /// TODO implement theme changing
    }

    private fun onClickListener(title: String) {
        when (title.lowercase()) {
            "my weight" -> {
                Navigation.createNavigateOnClickListener(R.id.action_navigation_setting_to_bodyDetailFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}