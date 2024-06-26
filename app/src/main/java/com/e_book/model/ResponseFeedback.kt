package com.e_book.model

data class ResponseFeedback(
    val id: Int,
    val userId: Int,
    val title: String,
    val message: String,
    val isActive: Boolean,
    val createdOn: String,
)