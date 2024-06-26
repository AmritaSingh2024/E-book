package com.e_book.model

data class RegistrationRequest(
    val userName: String,
    val password: String,
    val mobileNo: String,
    val dob: String,
    val address: String?,
    val gender: Int

)
