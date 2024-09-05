package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth

        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.pass.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            } else {
                binding.progress.isVisible = true
                login(email, password)
            }
        }

        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progress.isVisible = false // Hide progress after login attempt
                if (task.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val exception = task.exception
                    val errorMessage = when {
                        exception is com.google.firebase.auth.FirebaseAuthInvalidUserException -> {
                            "No account found with this email."
                        }

                        exception is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> {
                            "Incorrect Email or password.Please try again."
                        }

                        exception is com.google.firebase.auth.FirebaseAuthUserCollisionException -> {
                            "An account with this email already exists."
                        }

                        exception is com.google.firebase.auth.FirebaseAuthEmailException -> {
                            "Invalid email format."
                        }

                        else -> {
                            "Authentication failed: ${exception?.localizedMessage ?: "Unknown error"}"
                        }
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun sendPasswordResetEmail(email: String) {
        if (email.isBlank()) {
            binding.email.error = "Required!"
        } else {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to send password reset email: ${task.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
