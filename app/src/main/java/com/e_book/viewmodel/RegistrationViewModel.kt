package com.e_book.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.e_book.R
import com.e_book.model.RegistrationRequest
import com.e_book.model.RegistrationResponse
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    val registrationSuccessObserver = MutableLiveData<RegistrationResponse>()
    val registrationErrorObserver = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val mobileNo = MutableLiveData<String>()
    val dob = MutableLiveData<String>() // Store dob as String object
    val address = MutableLiveData<String>()
    private var gender: Int = 3

    companion object {
        private const val ADDRESS_MAX_LENGTH = 1000
    }

    interface DatePickerListener {
        fun showDatePicker()
    }

    var datePickerListener: DatePickerListener? = null

    fun setGender(selectedGender: Int) {
        gender = when (selectedGender) {
            R.id.maleRadioButton -> 0
            R.id.femaleRadioButton -> 1
            R.id.otherRadioButton -> 2
            else -> 3
        }
    }

    fun onDobClick() {
        datePickerListener?.showDatePicker()
    }

    fun registerUser() {
        val userNameValue = username.value
        val passwordValue = password.value
        val mobileNoValue = mobileNo.value
        val dobValue = dob.value
        val addressValue = address.value

        if (!userNameValue.isNullOrEmpty() && !passwordValue.isNullOrEmpty() && !mobileNoValue.isNullOrEmpty() && !dobValue.isNullOrEmpty() && !addressValue.isNullOrEmpty()) {
            if (addressValue!!.length > ADDRESS_MAX_LENGTH) {
                registrationErrorObserver.value = "Address cannot exceed $ADDRESS_MAX_LENGTH characters"
                return
            }

            val registrationRequest = RegistrationRequest(
                userName = userNameValue,
                password = passwordValue,
                mobileNo = mobileNoValue,
                dob = dobValue,
                address = addressValue,
                gender = gender
            )

            Log.d("RegistrationViewModel", "Registering user with request: $registrationRequest")

            viewModelScope.launch {
                ApiRepository.registerUser(
                    registrationRequest,
                    registrationSuccessObserver,
                    registrationErrorObserver
                )
            }
        } else {
            registrationErrorObserver.value = "Please fill in all fields"
        }
    }
}