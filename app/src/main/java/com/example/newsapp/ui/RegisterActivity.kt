package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var name: String
    private lateinit var mobile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        binding.aUser.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()
            name = binding.name.text.toString()
            mobile = binding.number.text.toString()

            if (email.isBlank() || pass.isBlank())
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            else if (pass.length < 6)
                Toast.makeText(this, "Short Password!", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.isVisible = true
                signUpUser(email, pass)
            }
        }
    }

    private fun signUpUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        verifyEmail()
                    } else {
                        handleSignUpFailure(task.exception)
                    }
                } else {
                    handleSignUpFailure(task.exception)
                }
            }
    }

    private fun handleSignUpFailure(exception: Exception?) {
        binding.progress.isVisible = false
        when (exception) {
            is FirebaseAuthUserCollisionException -> {
                Toast.makeText(this, "This email already exists!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                binding.progress.isVisible = false
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email for verification", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}