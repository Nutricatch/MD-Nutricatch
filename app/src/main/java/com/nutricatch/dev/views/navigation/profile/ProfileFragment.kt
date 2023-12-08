package com.nutricatch.dev.views.navigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutricatch.dev.databinding.FragmentProfileBinding
import com.nutricatch.dev.model.settingMenu

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter: SettingMenuAdapter by lazy {
        SettingMenuAdapter(::onClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel by viewModels<ProfileViewModel>()

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val settingMenu = settingMenu
        adapter.submitList(settingMenu)
        binding.rvSettings.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSettings.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvSettings.addItemDecoration(itemDecoration)

        return root
    }

    private fun onClickListener(title: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}