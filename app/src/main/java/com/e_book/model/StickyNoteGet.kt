package com.e_book.model

data class StickyNoteGet(
    val noteId: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val createdAt: Int,
    val updatedAt: Int,
    val user: String
)