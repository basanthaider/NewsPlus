package com.example.newsapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", 0)
        val checkedRB = binding.rbGroup.checkedRadioButtonId
        var chosenCountry: String = "us"
        binding.rbGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = binding.root.findViewById<RadioButton>(checkedId)
            chosenCountry = when (radioButton.text.toString()) {
                getString(R.string.united_states) -> "us"
                getString(R.string.united_kingdom) -> "uk"
                getString(R.string.egypt) -> "eg"
                getString(R.string.united_arab_emirates) -> "ae"
                else -> "ca"
            }
        }
        binding.applyChangesBtn.setOnClickListener {
             saveCountryPreference(chosenCountry)
            Toast.makeText(context, "Country:$chosenCountry is save successfully!! ", Toast.LENGTH_SHORT).show()

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