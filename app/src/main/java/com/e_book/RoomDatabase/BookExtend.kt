package com.e_book.RoomDatabase

data class BookExtend (   val title: String,
val coverImage: Int, // Assuming you are using drawable resource IDs for images
val pdfFile: String,
val expiryDate: String
)
