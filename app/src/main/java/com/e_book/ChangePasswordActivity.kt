package com.e_book

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.e_book.RoomDatabase.AppDatabase
import com.e_book.model.ChangepasswordRequest
import com.e_book.viewmodel.ChangePasswordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePasswordActivity : AppCompatActivity() {
    private val viewModel: ChangePasswordViewModel by viewModels()
    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var changePasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        oldPasswordEditText = findViewById(R.id.oldpassword)
        newPasswordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmpassword)
        changePasswordButton = findViewById(R.id.changePasswordButton)

        changePasswordButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (newPassword == confirmPassword) {
                CoroutineScope(Dispatchers.Main).launch {
                    val userId = getUserIdFromDatabase()
                    val request = ChangepasswordRequest(newPassword, oldPassword, userId = userId)
                    viewModel.changePassword(request)
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.changePasswordResponse.observe(this, Observer { response ->
            if (response != null) {
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Password change failed", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

    private suspend fun getUserIdFromDatabase(): Int {
        return withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(this@ChangePasswordActivity).appDao().getUserId()
        }
    }
}
