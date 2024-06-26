package com.e_book.model

data class CouponResponsItem(
    val couponId: Int,
    val couponCode: String,
    val discount: Int,
    val expirationDate: String,
    val isActive: Boolean,
    val isexiest: Any,
    val createdAt: String,
    val updatedAt: String,
    val tblUserBooks: List<Any>,

)