package com.e_book.model

data class AppVersionRequest(
    val aplicationname: String,
    val id: Int,
    val ip: String,
    val isActive: Boolean,
    val releaseDate: String,
    val versionName: String
)