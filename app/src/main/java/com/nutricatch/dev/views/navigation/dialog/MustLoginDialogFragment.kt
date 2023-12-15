package com.nutricatch.dev.views.navigation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nutricatch.dev.databinding.FragmentMustLoginDialogBinding

class MustLoginDialogFragment : DialogFragment() {
    private var _binding: FragmentMustLoginDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentMustLoginDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}