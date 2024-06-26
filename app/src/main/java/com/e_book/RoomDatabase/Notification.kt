package com.e_book.RoomDatabase

import androidx.room.Entity

@Entity(tableName = "tb_notification")
data class Notification(
    val count:String?,
    val createdAt: String,
    val filePath: String?,
    var imagePath: String?,
    val isActive: Boolean,
    val message: String,
    val notificationId: Int,
    var readAt: String?,
    val type: String,
    val updatedAt: String
)






