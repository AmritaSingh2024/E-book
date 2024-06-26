package com.e_book.model

data class FeedbackRequest(
    val userId: Int,
    val title: String,
    val message: String
)