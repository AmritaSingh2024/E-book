package com.e_book.model

data class GetBookListResponseItem(
    val author: String,
    val bookId: Int,
    val bookName: String,
    val couponId: Any,
    val coverImageLink: String,
    val createdAt: Any,
    val createdOn: String,
    val downloadLink: String,
    val isActive: Boolean,
    val paymentId: Any,
    val price: Int,
    val publicationDate: String,
    val purchaseDate: Any,
    val quantity: Int,
    val totalPrice: Int,
    val updatedAt: Any,
    val userBookId: Int,
    val userId: Int
)