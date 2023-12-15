package com.nutricatch.dev.views.navigation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nutricatch.dev.databinding.FragmentRegisterBinding
import com.nutricatch.dev.views.factory.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        with(binding) {
            val name = namaEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim().lowercase()
            val password = passwordEditText.text.toString().trim()

            viewModel.register(name, email, password).observe(viewLifecycleOwner){
                /// TODO observe di sini, untuk tiap resultnya
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}