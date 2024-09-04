package com.example.newsapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", 0)
        val checkedRB = binding.rbGroup.checkedRadioButtonId
        val chosenCountry = when (checkedRB) {
            R.id.us_rb -> "us"
            R.id.uk_rb -> "uk"
            R.id.eg_rb -> "eg"
            R.id.ae_rb -> "ae"
            else -> "ca"
        }
        val savedCountry = sharedPreferences.getString("chosenCountry", chosenCountry)

        binding.applyChangesBtn.setOnClickListener {
            val selectedCountry = binding.rbGroup.checkedRadioButtonId.toString()
            saveCountryPreference(selectedCountry)
        }

        return binding.root
    }

    private fun saveCountryPreference(chosenCountry: String) {
        with(sharedPreferences.edit()) {
            putString("chosenCountry", chosenCountry)
            apply()
        }
    }
}