package com.e_book.model

data class UpdateUserProfileRequest(
    val userId: Int,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dob:String,
    val gender: Int,
    val mobileNo: String,
    val address: String,
    val deviceId: String,

)