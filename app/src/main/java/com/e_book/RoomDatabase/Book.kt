package com.e_book.RoomDatabase
data class Book(
    val title: String,
    val coverImage: Int, // Assuming you are using drawable resource IDs for images
    val pdfFile: String,
    val publishDate: String,
    val readDate: String
)
