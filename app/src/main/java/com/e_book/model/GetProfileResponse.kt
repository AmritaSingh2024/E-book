package com.e_book.model

data class GetProfileResponse(
    val userId: Int,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dob: String?,
    val gender: Int,
    val mobileNo: String,
    val address: String,
    val password: String,

    val roleId: Int,
    val isActive: Boolean,
    val createdOn: String,
    val createdBy: Int,
    val updated: String,
    val updatedBy: Int,
    val lastLoginDate: String,
    val lastPasswordChange: String,
    val ip: String?,
    val deviceId: String?,
    val token: String?,
    val profile: String?,

)

