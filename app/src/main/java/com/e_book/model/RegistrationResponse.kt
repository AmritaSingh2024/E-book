package com.e_book.model
import java.util.Date

data class RegistrationResponse(
    val userName: String,
    val password: String,
    val mobileNo: String,
    val dob: String,
    val address: String,
    val gender: Int,
    val roleId: Int,
    val isActive: Boolean,
    val createdOn: String
)
