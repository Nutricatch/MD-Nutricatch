package com.nutricatch.dev.views.navigation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentLoginBinding
import com.nutricatch.dev.views.factory.AuthViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener(this)
        binding.tbRegister.setOnClickListener(this)
        binding.btnLoginWithGoogle.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        with(binding) {
            when (view) {
                btnLogin -> {
                    val email = emailEt.text.toString().trim().lowercase()
                    val password = passwordEt.text.toString().trim()
                    viewModel.login(email, password).observe(viewLifecycleOwner) { result ->
                        showLoading(true)

                        when (result) {
                            is ResultState.Success -> {
                                showLoading(false)
                                lifecycleScope.launch {
                                    if (result.data.accessToken != null) {
                                        viewModel.saveSession(result.data.accessToken)
                                        withContext(Dispatchers.Main) {
                                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationHome())
                                        }
                                    }

                                }
                            }

                            is ResultState.Error -> {
                                showLoading(false)
                                AlertDialog.Builder(requireActivity()).apply {
                                    setTitle("Something is Wrong")
                                    setMessage(result.error)
                                    setNegativeButton("Redo") { _, _ ->
                                    }
                                    create()
                                    show()
                                }
                            }

                            else -> {

                            }
                        }
                    }
                }

                btnLoginWithGoogle -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWebViewLoginFragment())
                }

                tbRegister -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }

                else -> {}
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    }
