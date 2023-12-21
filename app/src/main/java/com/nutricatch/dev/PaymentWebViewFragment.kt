package com.nutricatch.dev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nutricatch.dev.databinding.FragmentPaymentWebViewBinding
import com.nutricatch.dev.utils.Const
import com.nutricatch.dev.utils.showToast

class PaymentWebViewFragment : Fragment() {
    private var _binding: FragmentPaymentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timestamp = Const.timeStamp
        showToast(requireContext(), timestamp)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}