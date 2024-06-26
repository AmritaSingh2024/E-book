package com.e_book

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputFilter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.e_book.databinding.ActivityRegistrationBinding
import com.e_book.viewmodel.RegistrationViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistrationActivity : AppCompatActivity(), RegistrationViewModel.DatePickerListener {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.datePickerListener = this

        setupObservers()
        setupInputFilters()
    }

    private fun setupObservers() {
        viewModel.registrationSuccessObserver.observe(this, Observer { registrationResponse ->
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            // Navigate to appropriate screen after registration
        })

        viewModel.registrationErrorObserver.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            // Handle registration error
        })
    }

    private fun setupInputFilters() {
        // Set a filter to limit address input to 1000 characters
        val addressEditText: EditText = findViewById(R.id.address)
        val filterArray = arrayOf<InputFilter>(InputFilter.LengthFilter(1000))
        addressEditText.filters = filterArray
    }

    override fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                viewModel.dob.value = sdf.format(selectedDate.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}
