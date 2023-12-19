package com.nutricatch.dev.views.navigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.FragmentContactUsBinding
import com.nutricatch.dev.databinding.FragmentShareBinding

class ContactUsFragment : Fragment() {
    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }


}