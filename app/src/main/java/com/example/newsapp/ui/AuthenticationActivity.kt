package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityAuthenticationBinding
import com.example.newsapp.databinding.ActivityLoginBinding

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
       /* val startBtn = binding.startMbtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }*/
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },5000)


    }
}