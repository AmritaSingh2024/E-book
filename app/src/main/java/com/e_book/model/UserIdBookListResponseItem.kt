package com.e_book.model

data class UserIdBookListResponseItem(
    val author: String,
    val bookId: Int,
    val bookName: String,
    val couponId: Int,
    val coverImageLink: String,
    val createdAt: Any,
    val createdOn: String,
    val downloadLink: String,
    val isActive: Boolean,
    val paymentId: Int,
    val price: Int,
    val publicationDate: String,
    val purchaseDate:String,
    val quantity: Int,
    val totalPrice: Int,
    val updatedAt: Any,
    val userBookId: Int,
    val userId: Int
)