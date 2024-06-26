package com.e_book

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e_book.retrofit.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResetPasswordActivity : AppCompatActivity() {
/* private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var resetPasswordButton: Button
    private val TAG = "ResetPasswozrdActivity"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmpassword)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            when {
                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                }
                confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val userId = getUserId()
                    val changePasswordDto = ChangePasswordDto(userId, password)
                    changePassword(changePasswordDto)
                }
            }
        }
    }

    private fun getUserId(): Long {
        return sharedPreferences.getLong("userId", 0) // Default value 0, change as needed
    }

    private fun changePassword(changePasswordDto: ChangePasswordDto) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ebook.varcas.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)

        apiService.changePassword(changePasswordDto).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ResetPasswordActivity, "Password changed successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ResetPasswordActivity, "Error resetting password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ResetPasswordActivity, "Network Error", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Network request failed", t)
            }
        })
    }*/
}
