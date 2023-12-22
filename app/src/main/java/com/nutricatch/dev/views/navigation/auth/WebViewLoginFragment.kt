package com.nutricatch.dev.views.navigation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nutricatch.dev.BuildConfig
import com.nutricatch.dev.databinding.FragmentWebViewLoginBinding

class WebViewLoginFragment : Fragment() {
    private var _binding: FragmentWebViewLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webviewLoginGoogle.loadUrl("${BuildConfig.BASE_URL}/auth/google")
        binding.webviewLoginGoogle.settings.javaScriptEnabled = true

//        http://localhost:3000/auth/google/redirect?code=4%2F0AfJohXnrFtVrMfO09nThRoojF6klHtVjeMfwIf2xS9-LwjMw-QWv24suq8fz60ThmNtVdQ&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=5&hd=bangkit.academy&prompt=consent

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}