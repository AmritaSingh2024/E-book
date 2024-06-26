package com.e_book

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.e_book.DashBoard.DashBoardActivity
import com.e_book.databinding.ActivityLoginBinding
import com.e_book.viewmodel.LoginViewModel
import com.google.gson.JsonObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Check if user is already logged in
        if (isLoggedIn()) {
            navigateToDashboard()
            return
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.allApplicationObserver.observe(this) {
            it?.let {
                // Save login state
                saveLoggedIn(true)

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                navigateToDashboard()
                Log.d("loginsuccess","loginsuccess")
            }
        }

        viewModel.errorObserver.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })

        binding.loginButton.setOnClickListener {
            val userName = binding.email.text.toString()
            val password = binding.password.text.toString()

            // Create JsonObject for login request
            val loginJsonObject = loginJson(userName, password)
            viewModel.getLogin(this, loginJsonObject)
        }

      /*  val guestLogin = findViewById<TextView>(R.id.guestlogin)
        guestLogin.setOnClickListener {
            val intent = Intent(this, GuestedLoginActivity::class.java)
            startActivity(intent)
        }*/

        val registration = findViewById<TextView>(R.id.Register)
        registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

       /* val forgetPasswordTextView: TextView = findViewById(R.id.forgetpassword)
        forgetPasswordTextView.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun saveLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashBoardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginJson(username: String, password: String): JsonObject {
        val jsonObject = JsonObject()
        try {
            // Add username and password to JsonObject
            jsonObject.addProperty("userName", username)
            jsonObject.addProperty("password", password)
        } catch (e: Exception) {
            e.message
        }
        return jsonObject
    }
}
