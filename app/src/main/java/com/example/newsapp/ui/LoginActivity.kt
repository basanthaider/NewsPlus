package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
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
            if (email.isBlank() || password.isBlank())
                Toast.makeText(this, "Missing Field/s!", Toast.LENGTH_SHORT).show()
            else {
                binding.progress.isVisible = true
                startActivity(Intent(this, HomeActivity::class.java))
                login(email, password)
            }


        }
        binding.forgetPassword.setOnClickListener {
        }


    }
    //override fun onStart() {
      //  super.onStart()
        //val currentUser = auth.currentUser
        //if (currentUser != null && currentUser.isEmailVerified) {

          //  startActivity(Intent(this, HomeActivity::class.java))
            //finish()
        //}
    //}

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else
                        Toast.makeText(this, "Verify your email first", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    binding.progress.isVisible = false

                }
            }

    }

    private fun sendPasswordResetEmail(email: String) {
        if (email.isBlank())
            binding.email.error = "Required!"
        else {


        }
    }


    //public override fun onStart() {
    //  super.onStart()
    // Check if user is signed in (non-null) and update UI accordingly.
    // val currentUser = auth.currentUser
    //  if (currentUser != null) {

    // }
}





