package com.nutricatch.dev.views.navigation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.databinding.FragmentRegisterBinding
import com.nutricatch.dev.views.factory.AuthViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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

            viewModel.register(name, email, password).observe(viewLifecycleOwner){result->
                showLoading(true)
                /// TODO observe di sini, untuk tiap resultnya
                when(result)
                {
                    is ResultState.Success->{
                        showLoading(false)
                        AlertDialog.Builder(requireActivity()).apply {
                            setTitle("Welcome To Nutri Catch")
                            setMessage(result.data.message)
                            setPositiveButton("Great!"){_,_ ->
                            }
                            create()
                            show()
                        }
                        lifecycleScope.launch {
                            if (result.data.accessToken !=null)
                            {
                                viewModel.saveSession(result.data.accessToken)
                                withContext(Dispatchers.Main){
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
                    else->{

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}