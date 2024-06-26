package com.e_book.RoomDatabase
data class Coupon(
    val code: String,
    val expirationDate: String, // in the format "dd/MM/yyyy"
    val isValid: Boolean
)
