package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
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
                        Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                            .show()
                        binding.progress.isVisible = false

                    }
                }
            }
    }

    private fun addUserToFirestore() {
        val user = auth.currentUser
        user?.let {
            val uid = it.uid
            val email = it.email
            val userData = hashMapOf(
                "uid" to uid,
                "email" to email,
                "name" to name,
                "mobile" to mobile,
                //intialize with false
                "emailVerified" to false
            )

            // Save the user data in Firestore
            firestore.collection("users")
                .document(uid)
                .set(userData)
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving user data: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    binding.progress.isVisible = false
                }
        }
    }

    private fun verifyEmail() {
        val user = auth.currentUser
        if (user != null) {
            user.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.progress.isVisible = false
                        Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                        // Reload user to check email verification status
                        user.reload().addOnCompleteListener {
                            if (user.isEmailVerified) {
                                addUserToFirestore()  // Add user to Firestore only after email is verified
                            } else {
                                Toast.makeText(this, "Email not verified yet.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                .addOnFailureListener { exception ->
                    binding.progress.isVisible = false
                    Toast.makeText(
                        this,
                        "Error sending verification email: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("RegisterActivity", "Verification email error: ${exception.message}")
                }


        } else {

            Toast.makeText(this, "No user is signed in.", Toast.LENGTH_SHORT).show()
        }
    }

}


