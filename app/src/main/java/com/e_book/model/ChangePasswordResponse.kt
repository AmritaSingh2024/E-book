package com.e_book.model

data class ChangePasswordResponse(
    val newPassword: String,
    val oldPassword: String,
    val userId: Int
)