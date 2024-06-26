package com.e_book.RoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sticky_notes")
data class StickyNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val date: Long = System.currentTimeMillis() // Default to current timestamp
)
