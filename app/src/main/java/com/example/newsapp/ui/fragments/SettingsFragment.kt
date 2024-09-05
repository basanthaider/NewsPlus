package com.example.newsapp.ui.fragments

import android.content.Intent
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
import com.example.newsapp.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", 0)
        binding.rbGroup.checkedRadioButtonId
        val chosenCountryFromPref: String =
            sharedPreferences.getString("chosenCountry", "us").toString()
        var chosenCountry: String = chosenCountryFromPref
        //Log.d("trace", "Chosen country: $chosenCountryFromPref")
        when (chosenCountryFromPref) {
            "us" -> binding.rbGroup.check(R.id.us_rb)
            "gb" -> binding.rbGroup.check(R.id.uk_rb)
            "eg" -> binding.rbGroup.check(R.id.eg_rb)
            "ae" -> binding.rbGroup.check(R.id.ae_rb)
            else -> {
                chosenCountry = "ca"
                binding.rbGroup.check(R.id.ca_rb)
            }
        }
        //binding.rbGroup.check(R.id.ca_rb)
        binding.rbGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = binding.root.findViewById<RadioButton>(checkedId)
            chosenCountry = when (radioButton.text.toString()) {
                getString(R.string.united_states) -> "us"
                getString(R.string.united_kingdom) -> "gb"
                getString(R.string.egypt) -> "eg"
                getString(R.string.united_arab_emirates) -> "ae"
                else -> "ca"
            }
//            Log.d("trace", "Chosen country: $chosenCountry")
            saveCountryPreference(chosenCountry)
            Toast.makeText(
                context,
                "Country:$chosenCountry saved successfully!! ",
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

    private fun saveCountryPreference(chosenCountry: String) {
        with(sharedPreferences.edit()) {
            putString("chosenCountry", chosenCountry)
            apply()
        }
    }

    // Handle logout button click
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle logout button click
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            requireActivity().finish()
        }

        // Additional settings handling logic can be placed here
    }

}