package com.e_book.model

data class ChangepasswordRequest(
    val newPassword: String,
    val oldPassword: String,
    val userId: Int
)