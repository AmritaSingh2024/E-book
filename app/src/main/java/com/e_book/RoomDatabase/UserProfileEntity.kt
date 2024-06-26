package com.e_book.RoomDatabase
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val userId: Int,
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val gender: Int,
    val deviceId: String,
    val dob: String,
    val mobileNo: String?,
    val address: String?
)