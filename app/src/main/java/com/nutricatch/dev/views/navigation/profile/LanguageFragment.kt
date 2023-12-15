package com.nutricatch.dev.views.navigation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.prefs.dataStore
import com.nutricatch.dev.databinding.FragmentLanguageBinding
import com.nutricatch.dev.utils.Const
import com.nutricatch.dev.views.factory.PreferencesViewModelFactory

class LanguageFragment : Fragment() {
    private lateinit var binding: FragmentLanguageBinding
    private lateinit var preferences: Preferences
    private val viewModel by viewModels<ProfileViewModel> {
        PreferencesViewModelFactory(preferences)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        preferences = Preferences.getInstance(requireActivity().dataStore)
        binding =
            FragmentLanguageBinding.inflate(LayoutInflater.from(requireContext()), container, false)

        viewModel.locale.observe(viewLifecycleOwner) {
            Log.d("TAG", "onCreateView: $it")
            if (it == Const.LOCALE_EN) {
                binding.icChecklistId.visibility = View.INVISIBLE
                binding.icChecklist.visibility = View.VISIBLE
            } else {
                binding.icChecklistId.visibility = View.VISIBLE
                binding.icChecklist.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEnglish.setOnClickListener {
            val locale = LocaleListCompat.forLanguageTags(Const.LOCALE_EN)
            AppCompatDelegate.setApplicationLocales(locale)
            viewModel.setLocale(Const.LOCALE_EN)
            requireActivity().recreate()
        }

        binding.tvIndonesian.setOnClickListener {
            /// TODO nanti dibuat viewmodel
            val locale = LocaleListCompat.forLanguageTags(Const.LOCALE_ID)
            viewModel.setLocale(Const.LOCALE_ID)
            AppCompatDelegate.setApplicationLocales(locale)
            requireActivity().recreate()
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}