package com.nutricatch.dev.views.navigation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.databinding.FragmentLoginBinding
import com.nutricatch.dev.views.factory.AuthViewModelFactory

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        with(binding) {
            when (view) {
                btnLogin -> {
                    viewModel.login(
                        emailEt.text.toString().trim(),
                        passwordEt.text.toString().trim()
                    )
                }

                tbRegister -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }

                else -> {}
            }
        }
    }
}