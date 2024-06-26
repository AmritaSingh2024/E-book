package com.e_book.model

data class NotificationResponseItem(
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
