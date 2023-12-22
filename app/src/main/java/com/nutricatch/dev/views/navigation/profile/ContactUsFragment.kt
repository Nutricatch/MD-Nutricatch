package com.nutricatch.dev.views.navigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.FragmentContactUsBinding
import com.nutricatch.dev.databinding.FragmentShareBinding

class ContactUsFragment : Fragment() {
    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ContactUsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSendMessage.setOnClickListener{
            showLoading(true)
            viewModel.postMessage(binding.nameEt.toString(), binding.emailEt.toString(), binding.messageEt.toString())
            showLoading(false)
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Welcome To Nutri Catch")
                setMessage(viewModel.getStatus().toString())
                setPositiveButton("Great!"){_,_ ->
                }
                create()
                show()
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}


