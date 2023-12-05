package com.nutricatch.dev.views.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutricatch.dev.R
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentHomeBinding
import com.nutricatch.dev.views.factory.MainViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        MainViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.includeContentHome.rvLatestPost.layoutManager = layoutManager

        /// nanti, ubah jadi observe ke viewmodel
        val user = "John Doe"
        binding.toolbar.title = getString(R.string.home_greeting, user)

        viewModel.latestPosts.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    //showLoading(false)
                    val posts = result.data.latestPosts
                    val adapter = LatestPostAdapter()
                    adapter.submitList(posts)
                    binding.includeContentHome.rvLatestPost.adapter = adapter
                }

                is ResultState.Loading -> {
//                    showLoading(true)
                }

                is ResultState.Error -> {
//                    showLoading(false)

                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}