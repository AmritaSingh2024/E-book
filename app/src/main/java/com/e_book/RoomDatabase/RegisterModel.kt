package com.e_book.RoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_registration")
data class RegisterModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val mobileno: String,
    val email: String,
    val password: String,
    val confirmpassword: String,
    val address: String
)