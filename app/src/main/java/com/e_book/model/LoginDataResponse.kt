package com.e_book.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login")
data class LoginDataResponse(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val token: String,
    val userId: Int,
    val userName: String?
)
